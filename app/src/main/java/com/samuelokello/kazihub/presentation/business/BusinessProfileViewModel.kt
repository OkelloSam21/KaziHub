package com.samuelokello.kazihub.presentation.business

import android.content.Context
import android.location.Location
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileRequest
import com.samuelokello.kazihub.domain.repositpry.BusinessRepository
import com.samuelokello.kazihub.presentation.business.state.BusinessEvent
import com.samuelokello.kazihub.presentation.business.state.BusinessProfileState
import com.samuelokello.kazihub.presentation.common.location.LocationViewModel
import com.samuelokello.kazihub.utils.LocationManager
import com.samuelokello.kazihub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessProfileViewModel @Inject constructor(
    private val repository: BusinessRepository,
    private val locationManager: LocationManager,
    @ApplicationContext private val context: Context
) : ViewModel(),  LocationManager.LocationCallback , LocationViewModel {
    private val _state = MutableStateFlow(BusinessProfileState())
    val state = _state.asStateFlow()
    override val locationSuggestions: StateFlow<List<Place>> =
        _state.map { it.locationSuggestion }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    override fun onLocationReceived(location: Location) {
        LatLng(location.latitude, location.longitude)
        onLocationChange(location.toString())
    }

    override fun onLocationError(errorMessage: String) {
        _state.value = _state.value.copy(error = errorMessage)
    }

    override fun onLocationLatLngReceived(latLng: LatLng) {
        onLocationChange("${latLng.latitude}, ${latLng.longitude}")
    }
    override fun onLocationChange(location: String) {
        _state.value = _state.value.copy(location = location)
    }
    override fun fetchLocationSuggestions(query: String) {
        viewModelScope.launch {
            locationManager.fetchLocationSuggestions(
                locationQuery = query,
                onSuccess = { places: List<Place> ->
                    _state.update {suggestion ->
                        suggestion.copy(locationSuggestion = places)
                    }
                },
                onError = { errorMessage ->
                    _state.update {
                        it.copy(error = errorMessage)
                    }
                }
            )
        }
    }

    override fun clearSuggestions() {
        _state.update {
            it.copy(locationSuggestion = emptyList())
        }
    }

    private val sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)

    private val locationSuggestion = MutableLiveData<List<AutocompletePrediction>>()
    private fun showLoading() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
    }

    private fun hideLoading() {
        _state.update {
            it.copy(
                isLoading = false
            )
        }
    }

    fun updateLocation(latitude: Double, longitude: Double) {
        _state.update { it.copy(locationLatLng = LatLng(latitude, longitude)) }
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPhoneValid(phone: String): Boolean {
        return Patterns.PHONE.matcher(phone).matches()
    }

    private fun isFormComplete(email: String, phone: String, location: String): Boolean {
        return isEmailValid(email) && isPhoneValid(phone) && location.isNotEmpty()
    }

    fun onEvent(event: BusinessEvent) {
        when (event) {
            is BusinessEvent.OnEmailChanged -> {
                _state.update { it.copy(email = event.email) }
            }

            is BusinessEvent.OnLocationChanged -> {
                _state.update { it.copy(location = event.location) }
            }

            is BusinessEvent.OnPhoneNumberChanged -> {
                _state.update { it.copy(phone = event.phone) }
            }

            is BusinessEvent.OnBioChanged -> {
                _state.update { it.copy(bio = event.bio) }
            }

            is BusinessEvent.OnLocationInputChanged -> {
//                fetchLocationSuggestions(event.input)
            }

            is BusinessEvent.OnLocationSuggestionsFetched -> {
                locationSuggestion.value = event.suggestions
            }

            is BusinessEvent.OnCreateProfileClicked -> {
                val email = _state.value.email
                val phone = _state.value.phone
                val bio = _state.value.bio
                val location = _state.value.location
                val locationLatLng = _state.value.locationLatLng ?: return

                val request = BusinessProfileRequest(
                    email = email,
                    phone = phone,
                    bio = bio,
                    location = location,
                    latitude = locationLatLng.latitude,
                    longitude = locationLatLng.longitude
                )
                Log.e("ViewModel", "OnCreateProfileClicked")
//                if (isFormComplete(email, phone, location)) {
                    createProfile(request)
//                }

            }
        }
    }

    private fun createProfile(request: BusinessProfileRequest) {
        Log.e("createProfile: ", "createProfile:")
        viewModelScope.launch {
            showLoading()
            Log.e("BusinessProfileModel", "Making API call to create profile")
                when (val response = repository.createBusinessProfile(request)) {
                    is Resource.Success -> {
                        Log.e("createProfile: ","success" )
                        _state.update {
                            it.copy(
                                isLoading = true,
                                navigateToHome = true,
                            )
                        }
                        hideLoading()
                    }

                    is Resource.Error -> {
                        Log.e("createProfile: ","error" )
                        _state.update {
                            it.copy(
//                                            error = response.message ?: "An error occurred",
                                isLoading = false
                            )
                        }

                        Log.d(
                            "BusinessProfileModel",
                            "createProfile: ${response.message}"
                        )
                    }
                }
        }
    }

//    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
//    private fun fetchProfile() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val id: Int = 0
//            when (val result = repository.fetchBusinessProfileById(id)) {
//                is Resource.Success -> {
//                    val profile = result.data
//                    _state.update {
//                        it.copy(
//                            email = profile?.data?.email ?: "",
//                            phone = profile?.data?.phone ?: "",
//                            bio = profile?.data?.bio ?: "",
//                            location = profile?.data?.location ?: "",
//                        )
//                    }
//                }
//
//                is Resource.Error -> {
////                    _state.update {
////                        it
////                            .copy(error = result.message ?: "An error occurred")
////                    }
//                }
//            }
//        }
//    }

}





