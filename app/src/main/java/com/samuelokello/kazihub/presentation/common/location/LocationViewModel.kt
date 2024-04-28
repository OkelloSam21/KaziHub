package com.samuelokello.kazihub.presentation.common.location

import com.google.android.libraries.places.api.model.Place
import kotlinx.coroutines.flow.StateFlow

interface LocationViewModel {
    val locationSuggestions: StateFlow<List<Place>> // or List<Place>
    fun onLocationChange(location: String)
    fun fetchLocationSuggestions(query: String)

    fun clearSuggestions()
}
