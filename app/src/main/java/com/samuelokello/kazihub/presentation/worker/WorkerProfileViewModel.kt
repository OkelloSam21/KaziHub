package com.samuelokello.kazihub.presentation.worker

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.samuelokello.kazihub.data.repository.KaziHubRepository
import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileRequest
import com.samuelokello.kazihub.utils.LocationManager
import com.samuelokello.kazihub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/***
 * WorkerProfileViewModel is a ViewModel class that is responsible for handling the business logic of the WorkerProfileFragment.
 * @param repository is an instance of KaziHubRepository that is used to fetch and update the worker's profile.
 * @param locationProvider is an instance of LocationManager that is used to get the user's current location.
 * @param context is an instance of the application context.
 * */
@HiltViewModel
class WorkerProfileViewModel @Inject constructor(
    private val repository: KaziHubRepository,
    private val locationProvider: LocationManager,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _profile = MutableStateFlow(WorkerProfileState())
    val profile = _profile.asStateFlow()

    init {
        fetchProfile()
    }

    fun getPlacesClient() = locationProvider.getPlacesClient()

    fun onEmailChange(email: String) {
        _profile.value = _profile.value.copy(email = email)
    }

    fun onPhoneChange(newPhone: String) {
        _profile.value = _profile.value.copy(phone = newPhone)
    }

    fun onBioChange(newBio: String) {
        _profile.value = _profile.value.copy(bio = newBio)
    }

    fun onLocationChange(newLocation: String, latLng: LatLng) {
        _profile.value = _profile.value.copy(location = newLocation)
    }
    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPhoneValid(phone: String): Boolean {
        return Patterns.PHONE.matcher(phone).matches()
    }

    fun isFormComplete(email: String,phone: String,location: String): Boolean{
        return isEmailValid(email) && isPhoneValid(phone) && location.isNotEmpty()
    }

    // updateProfile is a public function that is used to update the worker's profile.
    fun updateProfile() {
        val email = _profile.value.email
        val phone = _profile.value.phone
        val bio = _profile.value.bio
        val location = _profile.value.location
        val latitude = _profile.value.latitude
        val longitude = _profile.value.longitude
        val request = BusinessProfileRequest(
            email = email,
            phone = phone,
            bio = bio,
            location = location,
            latitude = latitude,
            longitude = longitude
        )
        viewModelScope.launch {
            when(val response = repository.createBusinessProfile(request)) {
                is Resource.Success -> {
                    _profile.value = _profile.value.copy(navigateToHome = true)
                }
                is Resource.Error -> {
                    _profile.value = _profile.value.copy(error = response.message ?: "An error occurred")
                }
            }

        }
    }

    // fetchProfile is a private function that is used to fetch the worker's profile from the repository.
    private fun fetchProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            val id:Int = 0
            when(val response = repository.fetchBusinessProfile(id)) {
                is Resource.Success -> {
                    val profile = response.data
                    _profile.value = _profile.value.copy(
                        email = profile?.data?.email ?: "",
                        phone = profile?.data?.phone ?: "",
                        bio = profile?.data?.bio?: "",
                        location = profile?.data?.location?: "",
                        latitude = profile?.data?.lat?: 0.0,
                        longitude = profile?.data?.lon?: 0.0
                    )
                }
                is Resource.Error -> {
                    _profile.value = _profile.value.copy(error = response.message ?: "An error occurred")
                }
            }

        }
    }
}

data class WorkerProfileState(
    val isLoading: Boolean = false,
    val error: String = "",
    val email: String = "",
    val phone: String = "",
    val bio: String = "",
    val location: String = "",
    val latitude: Number = 0.0,
    val longitude: Number = 0.0,
    val navigateToHome: Boolean = false
)

