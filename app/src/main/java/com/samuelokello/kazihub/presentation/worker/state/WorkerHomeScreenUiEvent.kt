package com.samuelokello.kazihub.presentation.worker.state

import com.samuelokello.kazihub.domain.model.job.data

sealed interface WorkerHomeScreenUiEvent {
    data object OpenDrawer : WorkerHomeScreenUiEvent
    data object OnPullToRefresh : WorkerHomeScreenUiEvent
    data object OnViewAllClick : WorkerHomeScreenUiEvent
    data class JobSelected(val data: data) : WorkerHomeScreenUiEvent
}