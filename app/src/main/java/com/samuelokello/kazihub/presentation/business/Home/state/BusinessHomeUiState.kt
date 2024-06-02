package com.samuelokello.kazihub.presentation.business.Home.state

import com.samuelokello.kazihub.domain.model.job.Job

data class BusinessHomeUiState(
    val jobs: List<Job>? = emptyList(),
    val showBottomSheet: Boolean = false

)