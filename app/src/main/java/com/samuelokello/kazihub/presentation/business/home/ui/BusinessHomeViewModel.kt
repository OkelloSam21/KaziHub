package com.samuelokello.kazihub.presentation.business.home.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.kazihub.data.model.profile.ProfileResponse
import com.samuelokello.kazihub.domain.model.job.data
import com.samuelokello.kazihub.domain.model.proposal.proposalResponse.ProposalResponse
import com.samuelokello.kazihub.domain.repositpry.JobRepository
import com.samuelokello.kazihub.domain.repositpry.ProposalRepository
import com.samuelokello.kazihub.domain.uscase.GetCurrentUserUseCase
import com.samuelokello.kazihub.presentation.business.home.event.BusinessHomeUiEvents
import com.samuelokello.kazihub.presentation.business.home.state.BusinessHomeUiState
import com.samuelokello.kazihub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessHomeViewModel @Inject constructor(
    val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val jobRepository: JobRepository,
    private val proposalRepository: ProposalRepository
) : ViewModel() {
   private val _uiState = MutableStateFlow(BusinessHomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _businessProfile = MutableStateFlow<ProfileResponse?>(null)
    val businessProfile = _businessProfile.asStateFlow()

    private val _jobs = MutableStateFlow<List<data>>(emptyList())
    val jobs = _jobs.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _proposal = MutableStateFlow<ProposalResponse?>(null)
    val proposal = _proposal.asStateFlow()

    init {
        getCurrentBusinessProfile()
    }

    fun businessHomeEvent(event: BusinessHomeUiEvents) {
        when (event) {
            is BusinessHomeUiEvents.OnJobClick -> {}
            is BusinessHomeUiEvents.OnDrawerClick -> {
                _uiState.update { it.copy(
                    showDrawer = !it.showDrawer
                ) }
            }
            is BusinessHomeUiEvents.OnFABClick -> {}
        }
    }

    // Get current business profile
    private fun getCurrentBusinessProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            getCurrentUserUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _businessProfile.value = result.data
                        result.data?.data?.profile?.id?.let { profileId ->
                            fetchJobsForBusiness(profileId)

                        } ?: run {
                            _error.value = "Profile data is incomplete or missing"
                        }
                    }
                    is Resource.Error -> {
                        _error.value = result.message
                    }
                    is Resource.Loading -> {
                        _error.value = null
                    }
                }
            }
            _isLoading.value = false
        }
    }

    private fun fetchJobsForBusiness(businessId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = jobRepository.fetchJobByBusiness(businessId)) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    _jobs.value = result.data?.job ?: emptyList()
                    Log.e("BusinessHomeViewModel", "Profile ID: ${result.data?.job ?: emptyList()}")
                    _error.value = null
                }

                is Resource.Error -> _error.value = result.message
            }
            _isLoading.value = false
        }
    }

    // Other methods...


}

