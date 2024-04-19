package com.samuelokello.kazihub.presentation.business

import android.content.Context
import android.os.Build
import android.util.Log
import android.util.Patterns
import androidx.annotation.RequiresExtension
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.samuelokello.kazihub.data.repository.KaziHubRepository
import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileRequest
import com.samuelokello.kazihub.presentation.business.state.BusinessEvent
import com.samuelokello.kazihub.presentation.business.state.BusinessProfileState
import com.samuelokello.kazihub.utils.LocationManager
import com.samuelokello.kazihub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class BusinessProfileViewModel @Inject constructor(
    private val repository: KaziHubRepository,
    private val locationManager: LocationManager,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _state = MutableStateFlow(BusinessProfileState())
    val state = _state.asStateFlow()

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

    fun getPlacesClient() = locationManager.getPlacesClient()


    fun updateLocation(latitude: Double, longitude: Double) {
        _state.update { it.copy(locationLatLng = LatLng(latitude, longitude)) }
    }

    fun onLocationChange(newLocation: String, placeId: String) {
        _state.update { it.copy(location = newLocation, placeId = placeId) }
        if (placeId.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                locationManager.getLatLngFromPlaceId(placeId,
                    callback = { latLng ->
                        _state.update { it.copy(locationLatLng = latLng) }
                    },
                    errorCallback = { error ->
                        //handle error
                    })
            }
        }
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
                fetchLocationSuggestions(event.input)
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

                try {
                    viewModelScope.launch(Dispatchers.IO) {
                        showLoading()
                        Log.d("BusinessProfileModel", "Making API call to create profile")
                        if (isFormComplete(email, phone, location)) {
                            when (val response = repository.createBusinessProfile(request)) {
                                is Resource.Success -> {
                                    _state.update {
                                        it.copy(
                                            navigateToHome = true,
                                            isLoading = true
                                        )
                                    }
                                    hideLoading()
                                }

                                is Resource.Error -> {
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
                } catch (e: Exception) {
                    Log.e("BusinessProfileModel", "Exception Occurred: ${e.message}")
                    hideLoading()
                } finally {
                    hideLoading()
                }
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    private fun fetchProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            val id: Int = 0
            when (val result = repository.fetchBusinessProfileById(id)) {
                is Resource.Success -> {
                    val profile = result.data
                    _state.update {
                        it.copy(
                            email = profile?.data?.email ?: "",
                            phone = profile?.data?.phone ?: "",
                            bio = profile?.data?.bio ?: "",
                            location = profile?.data?.location ?: "",
                        )
                    }
                }

                is Resource.Error -> {
//                    _state.update {
//                        it
//                            .copy(error = result.message ?: "An error occurred")
//                    }
                }
            }
        }
    }

    private fun fetchLocationSuggestions(input: String) {
        //initialize places client
        val placesClient: PlacesClient = Places.createClient(context)

        // create a new token for autocomplete session
        val token = AutocompleteSessionToken.newInstance()

        // create a new request
        val request = FindAutocompletePredictionsRequest.builder()
            .setSessionToken(token)
            .setQuery(input)
            .build()

        // fetch predictions
        placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
            // Update the location suggestions in the state with the results
            val suggestions = response.autocompletePredictions.map { it }
            _state.update { it.copy(locationSuggestion = suggestions) }
        }.addOnFailureListener {
            // handle error
//            _state.update { it.copy(error = "An Error occurred") }
        }
    }
}





