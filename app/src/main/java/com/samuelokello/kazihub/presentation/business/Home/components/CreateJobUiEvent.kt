package com.samuelokello.kazihub.presentation.business.Home.components

import com.samuelokello.kazihub.domain.model.job.Category

sealed interface CreateJobUiEvent {
    data class OnTitleChange(val title: String): CreateJobUiEvent
    data class OnDescriptionChange(val description: String): CreateJobUiEvent
    data class OnBudgetChange(val budget: String): CreateJobUiEvent
    data class OnLocationChange(val location: String): CreateJobUiEvent
    data class OnQualificationsChange(val qualifications: String): CreateJobUiEvent
    data class OnCreateJobClick(
        val title: String,
        val description: String,
        val budget: String,
        val category: Category,
        val location: String,
        val qualifications: String
    ): CreateJobUiEvent

}
