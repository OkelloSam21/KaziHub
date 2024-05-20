package com.samuelokello.kazihub.presentation.worker.data

import android.content.Context
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.samuelokello.kazihub.domain.model.worker.WorkerProfileRequest
import com.samuelokello.kazihub.domain.repositpry.KaziHubRepository
import com.samuelokello.kazihub.presentation.worker.state.WorkerEvent
import com.samuelokello.kazihub.presentation.worker.state.WorkerProfileState
import com.samuelokello.kazihub.utils.LocationManager
import com.samuelokello.kazihub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/***
 * WorkerProfileViewModel is a ViewModel class that is responsible for handling the business logic of the WorkerProfileFragment.
 * @param repository is an instance of KaziHubRepository that is used to fetch and update the worker's profile.
 * @param locationProvider is an instance of LocationManager that is used to get the user's current location.
 * @param context is an instance of the application context.
 * */
@HiltViewModel
class CreateWorkerProfileViewModel @Inject constructor(
    private val repository: KaziHubRepository,
    private val locationProvider: LocationManager,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _state = MutableStateFlow(WorkerProfileState())
    val state = _state.asStateFlow()

    fun getPlacesClient() = locationProvider.getPlacesClient()

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPhoneValid(phone: String): Boolean {
        return Patterns.PHONE.matcher(phone).matches()
    }

    fun isFormComplete(): Boolean {
        return isEmailValid(_state.value.email) && isPhoneValid(_state.value.phone) && _state.value.location.isNotEmpty()
    }

    private suspend fun getLatLngFromSelectedPlace(placeId: String): LatLng =
        suspendCoroutine { continuation ->
            viewModelScope.launch {
                locationProvider.getLatLngFromPlaceId(
                    placeId = placeId,
                    callback = { fetchedLatLng ->
                        continuation.resume(fetchedLatLng)
                    },
                    errorCallback = { errorMessage ->
                        Log.e("CreateWorkerProfile", "Failed to get LatLng: $errorMessage")
                        continuation.resumeWithException(RuntimeException(errorMessage)) // return default LatLng on error
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
                _state.update { it.copy(location = event.location) }
                val searchJob = Job()
                searchJob.cancelChildren()
                viewModelScope.launch(Dispatchers.Main + searchJob) {
                    delay(2000)
                    locationProvider.fetchLocationSuggestions(
                        event.location,
                        onSuccess = {},
                        onError = {}
                    )
                }

            }

            is WorkerEvent.OnCreateProfileClicked -> {
                val email = _state.value.email
                val phone = _state.value.phone
                val bio = _state.value.bio
                val location = _state.value.location
//                val locationSuggestion = _state.value.locationSuggestion

                // Log the locationSuggestion the selected location
                Log.d("CreateWorkerProfile", "selected location: $location")
                viewModelScope.launch {
                    val latLng = getLatLngFromSelectedPlace(location)
                    val request = WorkerProfileRequest(
                        email = email,
                        phone = phone,
                        bio = bio,
                        location = location,
                        lat = latLng.latitude,
                        lon = latLng.longitude
                    )
                    createProfile(request = request)
                }
            }

            is WorkerEvent.OnAutoCompleteChanged -> {

            }
        }
    }

    // create Profile is a public function that is used to update the worker's profile.
    private fun createProfile(request: WorkerProfileRequest) {
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
