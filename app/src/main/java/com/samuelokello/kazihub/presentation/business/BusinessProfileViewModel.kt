package com.samuelokello.kazihub.presentation.business

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.util.Patterns
import androidx.annotation.RequiresExtension
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
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
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class BusinessProfileViewModel @Inject constructor(
    private val repository: KaziHubRepository,
    private val locationManager: LocationManager,
    @ApplicationContext private val context: Context
) : ViewModel()
{
    private val _profile = MutableStateFlow(BusinessProfileState())
    val profile = _profile.asStateFlow()


    init {
        fetchProfile()
    }
    fun getPlacesClient() = locationManager.getPlacesClient()
    fun onEmailChange(email: String) {
        _profile.value = _profile.value.copy(email = email)
    }

    fun onPhoneChange(newPhone: String) {
        _profile.value = _profile.value.copy(phone = newPhone)
    }

    fun onBioChange(newBio: String) {
        _profile.value = _profile.value.copy(bio = newBio)
    }

    fun onLocationChange(newLocation: String,placeId: String ) {
        _profile.value = _profile.value.copy(location = newLocation, placeId = placeId)
        if (placeId.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                locationManager.getLatLngFromPlaceId(placeId,
                    callback = { latLng->
                        _profile.value = _profile.value.copy(locationLatLng = latLng)
                    },
                    errorCallback ={error ->
                        //handle error
                    })
            }
        }
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



    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    suspend fun createProfile() {
        val email = _profile.value.email
        val phone = _profile.value.phone
        val bio = _profile.value.bio
        val location = _profile.value.location
        val locationLatLng = _profile.value.locationLatLng
        val latitude = locationLatLng?.latitude ?: 0.0
        val longitude =locationLatLng?.longitude ?: 0.0

        val request = BusinessProfileRequest(
            email = email,
            phone = phone,
            bio = bio,
            location = location,
            latitude = latitude,
            longitude = longitude
        )
        viewModelScope.launch {
            when (val response = repository.createBusinessProfile(request)) {
                is Resource.Success -> {
                    _profile.value = _profile.value.copy(navigateToHome = true)
                }

                is Resource.Error -> {
                    _profile.value =
                        _profile.value.copy(error = response.message ?: "An error occurred")
                }
            }

        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    private fun fetchProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            val id: Int = 0
            when (val result = repository.fetchBusinessProfile(id)) {
                is Resource.Success -> {
                    val profile = result.data
                    _profile.value = _profile.value.copy(
                        email = profile?.data?.email ?: "",
                        phone = profile?.data?.phone ?: "",
                        bio = profile?.data?.bio ?: "",
                        location = profile?.data?.location ?: "",
//                        latitude = profile?.data?.lat ?: 0.0,
//                        longitude = profile?.data?.lon ?: 0.0
                    )
                }

                is Resource.Error -> {
                    _profile.value =
                        _profile.value.copy(error = result.message ?: "An error occurred")
                }
            }
        }
    }

    suspend fun getCurrentLocation(): Location? {
        val fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(context)

        return if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.getCurrentLocation(
                LocationRequest.PRIORITY_HIGH_ACCURACY,
                null
            ).await()
        } else {
            null
        }
    }
}

data class BusinessProfileState(
    val isLoading: Boolean = false,
    val error: String = "",
    val email: String = "",
    val phone: String = "",
    val bio: String = "",
    val location: String = "",
    val locationLatLng: LatLng? = null,
//    val latitude: Number = 0.0,
//    val longitude: Number = 0.0,
    val placeId: String = "",
    val navigateToHome: Boolean = false
)


