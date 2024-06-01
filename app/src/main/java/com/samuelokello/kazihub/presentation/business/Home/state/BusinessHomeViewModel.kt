package com.samuelokello.kazihub.presentation.business.Home.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.kazihub.domain.repositpry.JobRepository
import com.samuelokello.kazihub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessHomeViewModel @Inject constructor(private val repository: JobRepository) :
    ViewModel() {

        init {
            fetchJobs()
        }

    private val _jobs = MutableStateFlow(BusinessHomeUiState())
    val jobs = _jobs.asStateFlow()
    fun createJob() {
        viewModelScope.launch {
            when(val response = repository.createJob()){
                is Resource.Success -> {

                }
                is Resource.Error -> {
                    val error = response.message
                }
            }
        }
    }

    fun fetchJobs(id: Int) {
        viewModelScope.launch {
            when(val response = repository.fetchJobByBusiness(id)){
                is Resource.Success -> {

                }
                is Resource.Error -> {
                    val error = response.message
                }
            }
        }

    }

}