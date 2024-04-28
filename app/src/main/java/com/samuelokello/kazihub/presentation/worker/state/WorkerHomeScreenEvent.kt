package com.samuelokello.kazihub.presentation.worker.state

import com.samuelokello.kazihub.presentation.worker.data.Job

sealed interface WorkerHomeScreenEvent {
    object OpenDrawer : WorkerHomeScreenEvent
    data class SearchQueryChanged(val query: String) : WorkerHomeScreenEvent
    data class SearchBarActiveChanged(val isActive: Boolean) : WorkerHomeScreenEvent
    object ViewAllPopularJobsClicked : WorkerHomeScreenEvent
    object ViewAllRecentPostsClicked : WorkerHomeScreenEvent
    data class JobSelected(val job: Job) : WorkerHomeScreenEvent
}