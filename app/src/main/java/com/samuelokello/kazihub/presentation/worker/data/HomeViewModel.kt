package com.samuelokello.kazihub.presentation.worker.data

import android.util.Log
import androidx.compose.material3.DrawerValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.kazihub.data.repository.KaziHubRepository
import com.samuelokello.kazihub.domain.model.job.JobResponse
import com.samuelokello.kazihub.presentation.worker.state.WorkerHomeScreenEvent
import com.samuelokello.kazihub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: KaziHubRepository
) : ViewModel() {
    private val _state = MutableStateFlow(WorkerHomeScreenUiState())
    val state = _state.asStateFlow()

    fun onEvent(event: WorkerHomeScreenEvent) {
        _state.update {
            when (event) {
                is WorkerHomeScreenEvent.OpenDrawer -> it.copy(drawerState = DrawerValue.Open)
                is WorkerHomeScreenEvent.SearchQueryChanged -> it.copy(searchQuery = event.query)
                is WorkerHomeScreenEvent.SearchBarActiveChanged -> it.copy(isSearchBarActive = event.isActive)
                is WorkerHomeScreenEvent.ViewAllPopularJobsClicked -> it.copy(selectedItemIndex = 0)
                is WorkerHomeScreenEvent.ViewAllRecentPostsClicked -> it.copy(selectedItemIndex = 1)
                is WorkerHomeScreenEvent.JobSelected -> it.copy(selectedItemIndex = 2)
            }
        }
    }

    init {
        fetchJobs()
    }

    private fun fetchJobs() {
        _state.value = WorkerHomeScreenUiState(isLoading = true)
        viewModelScope.launch {
            val response = repository.fetchAllJobs()
            Log.e("ViewModel", "fetchJobs: response = $response")
            if (response is Resource.Success) {
                Log.e("ViewModel", "fetchJobs: response.data = ${response.data}")
                val jobs = response.data?.map { mapJobResponseToJob(it) }
                Log.e("ViewModel", "fetchJobs: jobs = $jobs")
                _state.update { job -> job.copy(data = jobs ?: emptyList()) }
            }
            _state.value = WorkerHomeScreenUiState(isLoading = false)
        }
    }

    private fun updateData(jobs: List<Job>) {
        _state.update { it.copy(data = jobs) }
    }
    private fun mapJobResponseToJob(jobResponse: JobResponse): Job {
    return Job(
        id = jobResponse.id,
        title = jobResponse.title,
        desc = jobResponse.desc,
        budget = jobResponse.budget,
        location = jobResponse.location,
        postedOn = jobResponse.postedOn,
        status = jobResponse.status,
        business = jobResponse.business,
        businessId = jobResponse.businessId,
        category = jobResponse.category,
        categoryId = jobResponse.categoryId
    )
}
}