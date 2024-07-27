package com.samuelokello.kazihub.presentation.business.Home.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.kazihub.data.model.profile.ProfileResponse
import com.samuelokello.kazihub.domain.model.job.Job
import com.samuelokello.kazihub.domain.repositpry.AuthRepository
import com.samuelokello.kazihub.domain.repositpry.BusinessRepository
import com.samuelokello.kazihub.domain.repositpry.JobRepository
import com.samuelokello.kazihub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessHomeViewModel @Inject constructor(
    private val businessRepository: BusinessRepository,
    private val jobRepository: JobRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _businessProfile = MutableStateFlow<ProfileResponse?>(null)
    val businessProfile = _businessProfile.asStateFlow()

    private val _jobs = MutableStateFlow<List<Job>>(emptyList())
    val jobs = _jobs.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    init {
        getCurrentBusinessProfile()
    }


    // Get current business profile
    private fun getCurrentBusinessProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = authRepository.getCurrentUser()) {
                is Resource.Success -> {
                    _businessProfile.value = result.data

                    result.data?.let {
                        fetchJobsForBusiness(it.data.id)
                    }
                }
                is Resource.Error -> {
                    _error.value = result.message
                }
            }
            _isLoading.value = false
        }

    }

    private fun fetchJobsForBusiness(businessId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = jobRepository.fetchJobById(businessId)) {
                is Resource.Success -> {
                    _jobs.value = result.data?.job ?: emptyList()
                    _error.value = null
                }
                is Resource.Error -> _error.value = result.message
            }
            _isLoading.value = false
        }
    }

    // Other methods...
}

