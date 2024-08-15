package com.samuelokello.kazihub.presentation.business.job.state

import com.google.android.libraries.places.api.model.Place
import com.samuelokello.kazihub.domain.model.job.fetchById.Category

data class CreateJobUiState (
    val isLoading: Boolean = false,
    val title: String = "",
    val description: String = "",
    val budget: String = "",
    val location: String = "",
    val locationSuggestion : List<Place> = emptyList(),
    val selectedLocation : Place = Place.builder().build(),
    val category: List<Category> = emptyList(),
    val qualifications: String ="",
    val categoryId: Int = 0,
)
