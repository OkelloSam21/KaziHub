package com.samuelokello.kazihub.presentation.worker.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.samuelokello.kazihub.data.repository.KaziHubRepository
import com.samuelokello.kazihub.domain.model.worker.WorkerProfileRequest
import com.samuelokello.kazihub.domain.model.worker.image.WorkerProfileImageRequest
import com.samuelokello.kazihub.presentation.common.location.LocationViewModel
import com.samuelokello.kazihub.presentation.worker.state.WorkerEvent
import com.samuelokello.kazihub.presentation.worker.state.WorkerProfileState
import com.samuelokello.kazihub.utils.LocationManager
import com.samuelokello.kazihub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/***
 * WorkerProfileViewModel is a ViewModel class that is responsible for handling the business logic of the WorkerProfileFragment.
 * @param repository is an instance of KaziHubRepository that is used to fetch and update the worker's profile.
 * @param locationProvider is an instance of LocationManager that is used to get the user's current location.
 * @param context is an instance of the application context.
 * */
@SuppressLint("NewApi")
@HiltViewModel
class WorkerProfileViewModel @Inject constructor(
    private val repository: KaziHubRepository,
    private val locationProvider: LocationManager,
    @ApplicationContext private val context: Context
) : ViewModel(), LocationManager.LocationCallback , LocationViewModel {
    private val _state = MutableStateFlow(WorkerProfileState())
    val state = _state.asStateFlow()

    override val locationSuggestions: List<LatLng?> = _state.value.locationSuggestion

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

    fun getPlacesClient() = locationProvider.getPlacesClient()

    override fun onLocationChange(location: String) {
        _state.value = _state.value.copy(location = location)
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPhoneValid(phone: String): Boolean {
        return Patterns.PHONE.matcher(phone).matches()
    }

    fun isFormComplete(email: String, phone: String, location: String): Boolean {
        return isEmailValid(email) && isPhoneValid(phone) && location.isNotEmpty()
    }

    override fun fetchLocationSuggestions(query: String) {
        viewModelScope.launch {
            locationProvider.fetchLocationSuggestions(
                locationQuery = query,
                onSuccess = { places: List<Place> ->
                    _state.update { suggestion ->
                        suggestion.copy(locationSuggestion = places.map { it.latLng})
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

    fun onEvent(event: WorkerEvent) {
        when (event) {
            is WorkerEvent.OnEmailChanged -> {
                _state.update { it.copy(email = event.email) }
            }

            is WorkerEvent.OnPhoneNumberChanged -> {
                _state.update { it.copy(phone = event.phone) }
            }

            is WorkerEvent.OnBioChanged -> {
                _state.update { it.copy(bio = event.bio) }
            }

            is WorkerEvent.OnLocationChanged -> {
                // update state with new location
                _state.update { it.copy(location = event.location) }

                // Request location suggestions based on the new location
                fetchLocationSuggestions(event.location)
            }

            is WorkerEvent.OnCreateProfileClicked -> {
                createProfile()
            }
        }
    }

    // create Profile is a public function that is used to update the worker's profile.
    private fun createProfile() {
        val email = _state.value.email
        val phone = _state.value.phone
        val bio = _state.value.bio
        val location = _state.value.location
        val locationSuggestion = _state.value.locationSuggestion

        val selectedLatLng = locationSuggestion.find { it.toString() == location }

        if (selectedLatLng != null) {
            val latitude = selectedLatLng.latitude
            val longitude = selectedLatLng.longitude

            val request = WorkerProfileRequest(
                email = email,
                phone = phone,
                bio = bio,
                location = location,
                latitude = latitude,
                longitude = longitude
            )
            viewModelScope.launch {
                when (val response = repository.createWorkerProfile(request)) {
                    is Resource.Success -> {
                        Log.d("WorkerProfileViewModel", "${response.message}")
                        _state.value = _state.value.copy(navigateToHome = true)
                    }

                    is Resource.Error -> {
                        _state.value =
                            _state.value.copy(error = response.message ?: "An error occurred")
                    }
                }

            }
        }
    }

    // Function to fetch worker profile by ID

    private fun fetchProfile(id: Int) {
        viewModelScope.launch {
            when (val response = repository.fetchWorkerProfileById(id)) {
                is Resource.Success -> {
                    val profile = response.data
                    // Update ViewModel state with the fetched profile data
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
                    // Handle error case
                    _state.update { it.copy(error = response.message ?: "An error occurred") }
                }
            }
        }
    }

    // Function to update worker profile
    fun updateWorkerProfile(request: WorkerProfileRequest, id: Int) {
        viewModelScope.launch {
            when (val response = repository.updateWorkerProfile(id, request)) {
                is Resource.Success -> {
                    // Handle success case
                    _state.update { it.copy(successMessage = "Profile updated successfully!") }
                }

                is Resource.Error -> {
                    // Handle error case
                    _state.update {
                        it.copy(
                            error = response.message ?: "Failed to update profile"
                        )
                    }
                }
            }
        }
    }

    // Function to update worker profile image
    fun updateWorkerProfileImage(id: Int, request: WorkerProfileImageRequest) {
        viewModelScope.launch {
            when (val response = repository.updateWorkerProfileImage(id, request)) {
                is Resource.Success -> {
                    // Handle success case
                    _state.update { it.copy(successMessage = "Profile image updated successfully!") }
                }

                is Resource.Error -> {
                    // Handle error case
                    _state.update {
                        it.copy(
                            error = response.message ?: "Failed to update profile image"
                        )
                    }
                }
            }
        }
    }

    // Function to delete a worker skill
    fun deleteWorkerSkill(bearer: String, id: Int) {
        viewModelScope.launch {
            when (val response = repository.deleteSkill(bearer, id)) {
                is Resource.Success -> {
                    // Handle success case
                    _state.update { it.copy(successMessage = "Skill deleted successfully!") }
                }

                is Resource.Error -> {
                    // Handle error case
                    _state.update { it.copy(error = response.message ?: "Failed to delete skill") }
                }
            }
        }
    }
}
