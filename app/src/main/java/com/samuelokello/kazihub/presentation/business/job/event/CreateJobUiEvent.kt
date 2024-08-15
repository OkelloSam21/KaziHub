package com.samuelokello.kazihub.presentation.business.job.event

import com.google.android.libraries.places.api.model.Place
import com.samuelokello.kazihub.domain.model.job.category.Category

sealed interface CreateJobUiEvent {
    data class OnTitleChange(val title: String): CreateJobUiEvent
    data class OnDescriptionChange(val description: String): CreateJobUiEvent
    data class OnBudgetChange(val budget: String): CreateJobUiEvent
    data class OnLocationChange(val location: String): CreateJobUiEvent
    class OnSuggestionSelected(val suggestion: Place) : CreateJobUiEvent
    data class OnQualificationsChange(val qualifications: List<String>): CreateJobUiEvent
    data class OnCreateJobClick(
        val title: String,
        val description: String,
        val budget: String,
        val category: Int,
        val location: String,
        val qualifications: String
    ): CreateJobUiEvent

    data class OnCategoryChange(val item: Category) : CreateJobUiEvent
}
