package com.samuelokello.kazihub.presentation.worker.ui.job

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.kazihub.domain.model.job.fetchById.Business
import com.samuelokello.kazihub.domain.model.job.fetchById.Category
import com.samuelokello.kazihub.domain.model.job.fetchById.Data
import com.samuelokello.kazihub.domain.repositpry.JobRepository
import com.samuelokello.kazihub.domain.repositpry.ProposalRepository
import com.samuelokello.kazihub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobDetailsViewModel @Inject constructor(
    private val repository: JobRepository,
    private val proposalRepository: ProposalRepository
) : ViewModel() {
    private val _state = MutableStateFlow(JobDetailsUiState())
    val state = _state.asStateFlow()

    fun fetchJobById(jobId: Int) {
        _state.value = JobDetailsUiState(isLoading = true)
        viewModelScope.launch {
            when (val response = repository.fetchJobById(jobId)) {
                is Resource.Error -> handleError(response.message)
                is Resource.Success -> {
                    _state.value = JobDetailsUiState(
                        job = response.data?.data ?: Data(
                            budget = 0,
                            business = Business(
                                id = 0,
                                email = "",
                                phone = "",
                                img = "",
                                bio = "",
                                lat = 0,
                                lon = 0,
                                userId = 0,
                                isVerified = false,
                                joinedOn = ""
                            ),
                            category = Category(
                                id = 0,
                                name = ""
                            ),
                            description = "",
                            id = 0,
                            location = "",
                            postedOn = "",
                            qualifications = emptyList(),
                            title = ""
                        )
                    )
                }

                is Resource.Loading -> {}
            }
        }
    }

    private fun handleError(message: String?) {
        _state.value = JobDetailsUiState(error = message ?: "An unknown error occurred")
    }

    private fun onApplyClicked() {
        viewModelScope.launch {
            // Handle apply click
            _state.update {
                it.copy(
                    isLoading = true,
                    applied = true
                )
            }
        }
    }

    fun onEvent(event: JobDetailsUiEvent) {
        when (event) {
            is JobDetailsUiEvent.ApplyClicked -> onApplyClicked()
        }
    }
}

data class JobDetailsUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val applied: Boolean = false,
    val job: Data = Data(
        budget = 0,
        business = Business(
            id = 0,
            email = "",
            phone = "",
            img = "",
            bio = "",
            lat = 0,
            lon = 0,
            userId = 0,
            isVerified = false,
            joinedOn = "",
        ),
        category = Category(
            id = 0,
            name = ""
        ),
        description = "",
        id = 0,
        location = "",
        postedOn = "",
        qualifications = emptyList(),
        title = ""
    )
)

sealed class JobDetailsUiEvent {
    data object ApplyClicked : JobDetailsUiEvent()
}