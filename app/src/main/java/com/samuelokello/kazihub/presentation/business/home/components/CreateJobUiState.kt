package com.samuelokello.kazihub.presentation.business.home.components

import com.google.android.libraries.places.api.model.Place

data class CreateJobUiState (
    val title: String = "",
    val description: String = "",
    val budget: Int = 0,
    val location: String = "",
    val locationSuggestion : List<Place> = emptyList(),
    val selectedLocation : Place = Place.builder().build(),
    val category: String = "",
    val qualifications: List<String> = emptyList(),
    val categoryId: Int = 0,
)
