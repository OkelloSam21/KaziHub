package com.samuelokello.kazihub.presentation.worker.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.kazihub.domain.model.job.WorkerHomeScreenUiState
import com.samuelokello.kazihub.domain.model.job.data
import com.samuelokello.kazihub.domain.repositpry.BusinessRepository
import com.samuelokello.kazihub.domain.repositpry.JobRepository
import com.samuelokello.kazihub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: JobRepository,
    @ApplicationContext private val context: Context,
    private val businessRepository: BusinessRepository
) : ViewModel() {
    private val _state = MutableStateFlow(WorkerHomeScreenUiState())
    val state = _state.asStateFlow()


    init {
        fetchJobs()
        fetchRecentJobs()
    }

    private fun fetchJobs() {
        _state.value = WorkerHomeScreenUiState(isLoading = true)
        viewModelScope.launch {
            when (val response = repository.fetchAllJobs()) {
                is Resource.Error -> handleError(response.message)
                is Resource.Success -> {
                    Log.d("HomeViewModel", "fetchJobs Success: ${response.data?.job}")
                    handleJobsFetchSuccess(response.data?.job)
                }
                is Resource.Loading -> {
                    Log.d("HomeViewModel", "fetchJobs Loading")
                }
            }
        }
    }

    private fun fetchRecentJobs() {
        viewModelScope.launch {
            val limit = 5
            when (val response = repository.fetchRecentJobs(limit = limit)) {
                is Resource.Error -> handleError(response.message)
                is Resource.Success -> handleRecentJobsFetchSuccess(response.data?.job ?: emptyList())
                is Resource.Loading -> {}
            }
        }
    }

    private fun handleError(message: String?) {
        _state.update { it.copy(
            isLoading = false,
            error = message ?: "An unknown error occurred"
        ) }
        Toast.makeText(context, _state.value.error, Toast.LENGTH_SHORT).show()
    }

    private fun handleJobsFetchSuccess(jobs: List<data>?) {
        val mappedJobs = jobs?.map { mapJobResponseToJob(it) } ?: emptyList()
        Log.d("HomeViewModel", "All jobs fetched: ${mappedJobs.size}")
        _state.update { it.copy(
            isLoading = false,
            allData = mappedJobs,
            nearByJobs = mappedJobs
        ) }
    }

    private fun handleRecentJobsFetchSuccess(jobs: List<data>?) {
        val mappedJobs = jobs?.map { mapJobResponseToJob(it) } ?: emptyList()
        Log.d("HomeViewModel", "Recent jobs fetched: ${mappedJobs.size}")
        _state.update { it.copy(
            isLoading = false,
            recentJobs = mappedJobs
        ) }
    }

    fun fetchJobsByCategory(categoryId: Int) {
        viewModelScope.launch {
            when (val response = repository.fetchJobsByCategory(categoryId)) {
                is Resource.Error -> {
                    Log.e("ViewModel", "fetchJobsByCategory: response = ${response.message}")
                    response.message ?: "An unknown error occurred"
                    _state.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                }

                is Resource.Success -> {
                    val allJobs = response.data?.job?.map { mapJobResponseToJob(it) }
                    Log.e("ViewModel", "fetchJobsByCategory: response  data $allJobs")
                    _state.update {
                        it.copy(
                            isLoading = true,
                            allData = allJobs ?: emptyList(),
                            nearByJobs = allJobs ?: emptyList()
                        )
                    }
                }

                is Resource.Loading -> {}
            }

        }
    }

    private fun mapJobResponseToJob(jobData: data): data {
        return data(
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

    fun refreshAllData() {
        viewModelScope.launch {
            fetchJobs()
            fetchRecentJobs()

            _state.update { it.copy(isRefreshing = false) }
        }

    }
}