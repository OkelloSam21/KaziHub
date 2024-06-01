package com.samuelokello.kazihub.presentation.business.Home.state

import com.samuelokello.kazihub.presentation.worker.data.Job

data class BusinessHomeUiState (
    val jobs: List<Job> = emptyList(),
    val showBottomSheet: Boolean = false

)