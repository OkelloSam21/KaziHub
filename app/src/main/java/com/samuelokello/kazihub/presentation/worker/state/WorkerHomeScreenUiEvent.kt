package com.samuelokello.kazihub.presentation.worker.state

import com.samuelokello.kazihub.presentation.worker.data.Job

sealed interface WorkerHomeScreenUiEvent {
    data object OpenDrawer : WorkerHomeScreenUiEvent
    data object OnSearchClick : WorkerHomeScreenUiEvent
    data object OnPullToRefresh : WorkerHomeScreenUiEvent
    data class NavigateToJobDetails(val jobId: Int) : WorkerHomeScreenUiEvent
    data class SearchQueryChanged(val query: String) : WorkerHomeScreenUiEvent
    data object OnViewAllClick : WorkerHomeScreenUiEvent
    data object OnSHowAllClick : WorkerHomeScreenUiEvent
    data class JobSelected(val job: Job) : WorkerHomeScreenUiEvent
}