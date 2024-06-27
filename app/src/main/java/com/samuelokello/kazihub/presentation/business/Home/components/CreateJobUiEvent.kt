package com.samuelokello.kazihub.presentation.business.Home.components

sealed interface CreateJobUiEvent {
    data class OnTitleChange(val title: String): CreateJobUiEvent
    data class OnDescriptionChange(val description: String): CreateJobUiEvent
    data class OnBudgetChange(val budget: Int): CreateJobUiEvent
    data class OnLocationChange(val location: String): CreateJobUiEvent
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
