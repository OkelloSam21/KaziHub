package com.samuelokello.kazihub.presentation.common.location

import com.google.android.gms.maps.model.LatLng

interface LocationViewModel {
    val locationSuggestions: List<LatLng?> // or List<Place>
    fun onLocationChange(location: String)
    fun fetchLocationSuggestions(query: String)
}
