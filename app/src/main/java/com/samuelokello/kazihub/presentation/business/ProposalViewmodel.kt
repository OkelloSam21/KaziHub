package com.samuelokello.kazihub.presentation.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.kazihub.domain.model.proposal.proposalResponse.ProposalResponseItem
import com.samuelokello.kazihub.domain.repositpry.ProposalRepository
import com.samuelokello.kazihub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProposalViewmodel @Inject constructor(private val proposalRepository: ProposalRepository): ViewModel() {
    val _uiState = MutableStateFlow(ProposalUiState())
    val uiState = _uiState.asStateFlow()

    private fun fetchProposalsBYId(id: Int) {
        viewModelScope.launch {
            when (val result = proposalRepository.getProposalById(id)) {
                is Resource.Error -> {
                   _uiState.update {
                       it.copy(
                           isLoading = false,
                           error = result.message
                       )
                   }
                }
                is Resource.Loading -> {
                   _uiState.update {
                       it.copy(
                           isLoading = true,
                           error = null
                       )
                   }
                }
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = null,
                            proposals = result.data ?: emptyList()
                        )
                    }
                }
            }
        }
    }
}

data class ProposalUiState (
    val isLoading: Boolean = false,
    val error: String? = null,
    val proposals: List<ProposalResponseItem> = emptyList()
)
