package com.samuelokello.kazihub.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.samuelokello.kazihub.R
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationManager @Inject constructor(private val context: Context) {
    private val focusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private val placesClient: PlacesClient

    init {
        Places.initializeWithNewPlacesApiEnabled(context, context.getString(R.string.maps_api_key))
        placesClient = Places.createClient(context)
    }

    interface LocationCallback {
        fun onLocationReceived(location: Location)
        fun onLocationError(errorMessage: String)
        fun onLocationLatLngReceived(latLng: LatLng)
    }

    fun checkAndRequestLocationPermission(activity: Activity) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    fun getUserLocation(callback: LocationCallback) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            callback.onLocationError("Location permission not granted")
            return
        }
        focusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                callback.onLocationReceived(location)
                val latLng = LatLng(location.latitude, location.longitude)
                callback.onLocationLatLngReceived(latLng)
            } else {
                callback.onLocationError("Failed to get user location")
            }
        }.addOnFailureListener { exception ->
            callback.onLocationError("Failed to get user location: ${exception.message}")
        }
    }

    suspend fun getLatLngFromPlaceId(placeId: String, callback: (LatLng) -> Unit, errorCallback: (String) -> Unit) {
        val placeFields = listOf(Place.Field.LAT_LNG)
        val fetchPlaceRequest = FetchPlaceRequest.newInstance(placeId, placeFields)

        try {
            val placeResponse = placesClient.fetchPlace(fetchPlaceRequest).await()
            val latLng = placeResponse.place.latLng
            if (latLng != null) {
                callback.invoke(latLng)
            } else {
                errorCallback.invoke("Failed to get latitude and longitude")
            }
        } catch (e: Exception) {
            errorCallback.invoke("Error fetching place details: ${e.message}")
        }
    }

    fun getPlacesClient() = placesClient

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
    }
}


//class LocationManager @Inject constructor(private val context: Context) {
//    private val focusedLocationClient: FusedLocationProviderClient =
//        LocationServices.getFusedLocationProviderClient(context)
//    private val placesClient: PlacesClient
//
//    init {
//        Places.initializeWithNewPlacesApiEnabled(context, context.getString(R.string.maps_api_key))
//        placesClient = Places.createClient(context)
//    }
//
//    fun checkAndRequestLocationPermission(): Boolean {
//        if (ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            if (context is Activity) {
//                ActivityCompat.requestPermissions(
//                    context,
//                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                    1
//                )
//            } else {
//                Log.e(TAG, "Context is not an activity")
//            }
//            return false
//        }
//        return true
//    }
//
//    fun getUserLocation(hasLocationPermission: Boolean, input: String) {
//
//        val token = AutocompleteSessionToken.newInstance()
//
//        val request = FindAutocompletePredictionsRequest.builder()
//            .setSessionToken(token)
//            .setQuery(input)
//            .build()
//
//        placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
//            for (prediction in response.autocompletePredictions) {
//                Log.i(TAG, prediction.getFullText(null).toString())
//            }
//        }.addOnFailureListener { exception ->
//            if (exception is ApiException) {
//                Log.e(TAG, "Place not found: " + exception.statusCode)
//            }
//        }
//
//
//        if (!hasLocationPermission) {
//            ActivityCompat.requestPermissions(
//                context as Activity,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                1
//            )
//        }
//
//
//        if (ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return
//        }
//        focusedLocationClient.lastLocation.addOnSuccessListener { location ->
//            if (location != null) {
//
//            }
//        }
//
//    }
//
//    suspend fun getLatLngFromPlaceId(placeId:String): LatLng{
//        val placeFields = listOf(Place.Field.LAT_LNG)
//        val fetchPlaceRequest = FetchPlaceRequest.newInstance(placeId, placeFields)
//
//        return try {
//            val placeResponse = placesClient.fetchPlace(fetchPlaceRequest).await()
//            placeResponse.place.latLng!!
//        } catch (e: Exception) {
//            LatLng(0.0, 0.0)
//        }
//    }
//
//    fun getPlacesClient() = placesClient
//
//}