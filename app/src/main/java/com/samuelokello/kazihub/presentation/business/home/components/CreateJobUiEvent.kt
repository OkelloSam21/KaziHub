package com.samuelokello.kazihub.presentation.business.home.components

import com.google.android.libraries.places.api.model.Place

sealed interface CreateJobUiEvent {
    data class OnTitleChange(val title: String): CreateJobUiEvent
    data class OnDescriptionChange(val description: String): CreateJobUiEvent
    data class OnBudgetChange(val budget: Int): CreateJobUiEvent
    data class OnLocationChange(val location: String): CreateJobUiEvent
    class OnSuggestionSelected(val suggestion: Place) : CreateJobUiEvent
    data class OnQualificationsChange(val qualifications: String): CreateJobUiEvent
    data class OnCreateJobClick(
        val title: String,
        val description: String,
        val budget: Int,
        val category: String,
        val location: String,
        val qualifications: String
    ): CreateJobUiEvent

    data class OnCategoryChange(val item: String) : CreateJobUiEvent
}
