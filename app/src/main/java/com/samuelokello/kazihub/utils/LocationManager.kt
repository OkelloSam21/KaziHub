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

/**
 * This class manages location related functionalities including permission checks,
 * retrieving user's current location, and getting LatLng from a Place ID.
 */
class LocationManager @Inject constructor(private val context: Context) {
    // Used to get the user's location
    private val focusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    // Client for interacting with Google Places API
    private val placesClient: PlacesClient

    init {
        Places.initializeWithNewPlacesApiEnabled(context, context.getString(R.string.maps_api_key))
        placesClient = Places.createClient(context)
    }

    /**
     * Interface for receiving location updates and errors.
     */
    interface LocationCallback {
        fun onLocationReceived(location: Location)
        fun onLocationError(errorMessage: String)
        fun onLocationLatLngReceived(latLng: LatLng)
    }

    /**
     * Checks if location permission is granted and requests it if not.
     *
     * @param activity The activity requesting permission.
     */
    fun checkAndRequestLocationPermission(activity: Activity) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request fine location permission if not granted
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    /**
     * Retrieves the user's current location and calls the provided callback with the results.
     *
     * @param callback The callback to receive location updates and errors.
     */
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
        // Use FusedLocationProviderClient to get user's last known location
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
    /**
     * Fetches the LatLng from a given Place ID using Places API asynchronously.
     *
     * @param placeId The ID of the Place to get LatLng from.
     * @param callback The callback to receive the LatLng if successful.
     * @param errorCallback The callback to receive an error message if unsuccessful.
     */
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