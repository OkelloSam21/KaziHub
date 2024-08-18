package com.samuelokello.kazihub.presentation.business

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.kazihub.domain.model.proposal.proposalResponse.ProposalData
import com.samuelokello.kazihub.domain.repositpry.ProposalRepository
import com.samuelokello.kazihub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProposalViewModel @Inject constructor(private val repository: ProposalRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ProposalUiState())
    val uiState = _uiState.asStateFlow()

    fun fetchProposalById(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                when (val response = repository.getProposalById(id)) {
                    is Resource.Error -> {
                        _uiState.update { it.copy(isLoading = false, error = response.message) }
                        Log.e("ProposalViewModel", "Error: ${response.message}")
                    }
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        Log.d("ProposalViewModel", "Response: ${response.data}")
                        val proposalData = response.data?.data
                        if (proposalData != null) {
                            val proposal = ProposalData(
                                amount = proposalData.amount,
                                cv = proposalData.cv,
                                id = proposalData.id,
                                jobId = proposalData.jobId,
                                message = proposalData.message,
                                status = proposalData.status,
                                userId = proposalData.userId
                            )
                            _uiState.update { it.copy(
                                isLoading = false,
                                proposals = listOf(proposal)
                            ) }
                            Log.d("ProposalViewModel", "Proposal parsed: $proposal")
                        } else {
                            _uiState.update { it.copy(
                                isLoading = false,
                                message = response.data?.message ?: "No proposal data found"
                            ) }
                            Log.d("ProposalViewModel", "No proposal data, message: ${response.data?.message}")
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
                Log.e("ProposalViewModel", "Exception: ${e.message}", e)
            }
        }
    }
}

data class ProposalUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val proposals: List<ProposalData> = emptyList(),
    val message: String? = null
)