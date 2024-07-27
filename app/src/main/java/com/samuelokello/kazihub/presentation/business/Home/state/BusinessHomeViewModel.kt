package com.samuelokello.kazihub.presentation.business.Home.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileResponse
import com.samuelokello.kazihub.domain.model.job.Job
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
    private val jobRepository: JobRepository
) : ViewModel() {

    private val _businessProfile = MutableStateFlow<BusinessProfileResponse?>(null)
    val businessProfile = _businessProfile.asStateFlow()

    private val _jobs = MutableStateFlow<List<Job>>(emptyList())
    val jobs = _jobs.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    init {
        fetchBusinessProfile()
    }

    private fun fetchBusinessProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = businessRepository.fetchBusinessProfiles()) {
                is Resource.Success -> {
                    _businessProfile.value = result.data?.firstOrNull()
                    _businessProfile.value?.data?.id?.let { fetchJobsForBusiness(it) }
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
                is Resource.Success -> {}
                is Resource.Error -> _error.value = result.message
            }
            _isLoading.value = false
        }
    }

    // Other methods...
}

