package com.samuelokello.kazihub.presentation.business.Home.state

import com.samuelokello.kazihub.domain.model.job.data

data class BusinessHomeUiState(
    val jobs: List<data>? = emptyList(),
    val showBottomSheet: Boolean = false

)