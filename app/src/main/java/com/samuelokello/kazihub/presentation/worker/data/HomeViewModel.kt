package com.samuelokello.kazihub.presentation.worker.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.kazihub.domain.model.job.Data
import com.samuelokello.kazihub.domain.repositpry.BusinessRepository
import com.samuelokello.kazihub.domain.repositpry.JobRepository
import com.samuelokello.kazihub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: JobRepository,
    private val businessRepository: BusinessRepository
) : ViewModel() {
    private val _state = MutableStateFlow(WorkerHomeScreenUiState())
    val state = _state.asStateFlow()

    init {
        fetchJobs()
//        fetchRecentJobs()
    }

    private fun fetchJobs() {
        _state.value = WorkerHomeScreenUiState(isLoading = true)
        viewModelScope.launch {
            when (val response = repository.fetchAllJobs()) {
                is Resource.Error -> {
                    Log.e("ViewModel", "fetchJobs: response = ${response.message}")
                    response.message ?: "An unknown error occurred"
                    _state.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                }

                is Resource.Success -> {
                    val allJobs = response.data?.data?.map { mapJobResponseToJob(it) }
                    Log.e("ViewModel", "fetchJobs: response  job $allJobs")
                    _state.update {
                        it.copy(
                            isLoading = true,
                            allJobs = allJobs ?: emptyList(),
                            nearByJobs = allJobs ?: emptyList()
                        )
                    }
                }
            }
        }
    }

    private fun fetchRecentJobs() {
        viewModelScope.launch {
            val limit = 5
            when (val response = repository.fetchRecentJobs(limit = limit)) {
                is Resource.Error -> {
                    Log.e("ViewModel", "fetchRecentJobs: response = ${response.data}")

                    _state.update {
                            it.copy(
                                isLoading = false,
                                recentJobs = response.data?.data?.map {recentJobResponse -> mapJobResponseToJob(recentJobResponse) } ?: emptyList()
                            )
                    }
                }

                is Resource.Success -> {
                    _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun fetchJobsByCategory(categoryId: String) {
        viewModelScope.launch {

        }
    }

    private fun mapJobResponseToJob(jobData: Data ): Job {
    return Job(
        id = jobData.id,
        title = jobData.title,
        desc = jobData.desc,
        budget = jobData.budget,
        location = jobData.location,
        postedOn = jobData.postedOn,
        status = jobData.status,
        business = jobData.business,
        businessId = jobData.businessId,
        category = jobData.category,
        categoryId = jobData.categoryId
    )
}
}