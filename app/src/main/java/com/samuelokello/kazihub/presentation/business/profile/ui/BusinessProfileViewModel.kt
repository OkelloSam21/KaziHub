package com.samuelokello.kazihub.presentation.business.profile.ui

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileRequest
import com.samuelokello.kazihub.domain.repositpry.BusinessRepository
import com.samuelokello.kazihub.presentation.business.home.event.BusinessEvent
import com.samuelokello.kazihub.presentation.business.home.state.BusinessProfileState
import com.samuelokello.kazihub.utils.LocationManager
import com.samuelokello.kazihub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessProfileViewModel @Inject constructor(
    private val repository: BusinessRepository,
    private val locationManager: LocationManager,
) : ViewModel() {
    private val _state = MutableStateFlow(BusinessProfileState())
    val state = _state.asStateFlow()


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

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPhoneValid(phone: String): Boolean {
        return Patterns.PHONE.matcher(phone).matches()
    }

    fun isFormComplete(): Boolean {
        return isEmailValid(_state.value.email) && isPhoneValid(_state.value.phone) && _state.value.location.isNotEmpty()
    }

    fun onEvent(event: BusinessEvent) {
        when (event) {
            is BusinessEvent.OnEmailChanged -> {
                _state.update { it.copy(email = event.email) }
            }

            is BusinessEvent.OnLocationChanged -> {
                _state.update { it.copy(location = event.location) }

                val searchJob = Job()
                searchJob.cancelChildren()
                viewModelScope.launch(Dispatchers.Main + searchJob) {
                    delay(3000)

                    locationManager.fetchLocationSuggestions(
                        event.location,

                        onSuccess = { suggestions ->
                            _state.update {
                                it.copy(locationSuggestion = suggestions)
                            }
                        },

                        onError = { errorMessage ->
                            _state.update {
                                it.copy(
                                    locationSuggestion = emptyList(),
                                    error = errorMessage
                                )
                            }
                        }

                    )
                }
            }

            is BusinessEvent.OnPhoneNumberChanged -> {
                _state.update { it.copy(phone = event.phone) }
            }

            is BusinessEvent.OnBioChanged -> {
                _state.update { it.copy(bio = event.bio) }
            }

            is BusinessEvent.OnCreateProfileClicked -> {
                val email = _state.value.email
                val phone = _state.value.phone
                val bio = _state.value.bio
                val location = _state.value.location
                val coordinates = event.selectedLocation.latLng!!
                val request = BusinessProfileRequest(
                    email = email,
                    phone = phone,
                    bio = bio,
                    location = location,
                    latitude = coordinates.latitude,
                    longitude = coordinates.longitude
                )
                createProfile(request)

            }

            is BusinessEvent.OnSuggestionSelected -> {
                _state.update {
                    event.suggestion.name?.let { selectedLocation ->
                        it.copy(
                            location = selectedLocation,
                            selectedLocation = event.suggestion
                        )
                    }!!
                }
            }

        }
    }

    private fun createProfile(request: BusinessProfileRequest) {
        Log.e("createProfile: ", "createProfile:")
        viewModelScope.launch {
            showLoading()
            Log.e("BusinessProfileModel", "Making API call to create profile")
            when (val response = repository.createBusinessProfile(request)) {
                is Resource.Loading -> {
                    Log.e("createProfile: ", "loading")
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is Resource.Success -> {
                    Log.e("createProfile: ", "success")
                    _state.update {
                        it.copy(
                            isLoading = true,
                            navigateToHome = true,
                        )
                    }
                    hideLoading()
                }

                is Resource.Error -> {
                    Log.e("createProfile: ", "error")
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

}





