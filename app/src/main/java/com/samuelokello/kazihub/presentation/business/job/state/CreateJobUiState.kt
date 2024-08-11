package com.samuelokello.kazihub.presentation.business.job.state

import com.google.android.libraries.places.api.model.Place
import com.samuelokello.kazihub.domain.model.job.fetchById.Category

data class CreateJobUiState (
    val title: String = "",
    val description: String = "",
    val budget: Int = 0,
    val location: String = "",
    val locationSuggestion : List<Place> = emptyList(),
    val selectedLocation : Place = Place.builder().build(),
    val category: List<Category> = emptyList(),
    val qualifications: List<String> = emptyList(),
    val categoryId: Int = 0,
)
