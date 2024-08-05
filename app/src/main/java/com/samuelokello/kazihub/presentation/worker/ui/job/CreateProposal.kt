package com.samuelokello.kazihub.presentation.worker.ui.job

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.kazihub.domain.model.proposal.createProposal.ProposalRequest
import com.samuelokello.kazihub.domain.repositpry.ProposalRepository
import com.samuelokello.kazihub.presentation.common.components.CustomButton
import com.samuelokello.kazihub.presentation.shared.components.EditTextField
import com.samuelokello.kazihub.ui.theme.KaziHubTheme
import com.samuelokello.kazihub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// create a bottom sheet to create a proposal
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProposal(
    onDismiss: () -> Unit,
    onSubmit : () -> Unit,
    viewModel: CreateProposalViewModel = hiltViewModel()
) {
    val modalSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.PartiallyExpanded }
    )
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        modalSheetState.show()

    }
    if (state.error.isNotEmpty())
        Toast.makeText(LocalContext.current, state.error, Toast.LENGTH_LONG).show()
    if (state.success.isNotEmpty())
        Toast.makeText(LocalContext.current, state.success, Toast.LENGTH_LONG).show()

    LaunchedEffect (state.navigateToHome) {
        if (state.navigateToHome) {
            onSubmit()
            modalSheetState.hide()
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = modalSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        ProposalRequestForm(
            modifier = Modifier,
            event = viewModel::onEvent,
            state = viewModel.state.collectAsState().value
        )
    }
}

@Composable
fun ProposalRequestForm(
    modifier: Modifier = Modifier,
    event: (ProposalEvent) -> Unit = {},
    state: ProposalUiState

) {
    Column(
        modifier = modifier
    ) {

        Text(
            text = "Create Proposal",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Column {
            EditTextField(
                label = "Amount",
                value = state.amount,
                onValueChange = { event(ProposalEvent.AmountChanged(it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                ),
                singleLine = true,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(16.dp))


            EditTextField(
                label = "Cv",
                value = state.cv,
                onValueChange = { event(ProposalEvent.CvChanged(it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                ),
                singleLine = false,
                modifier = Modifier
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomButton(
                onClick = {event(ProposalEvent.Submit)},
                text = "Submit",
                isEnabled = state.amount.isNotEmpty() && state.cv.isNotEmpty()
            )
        }
    }
}

@HiltViewModel
class CreateProposalViewModel
@Inject constructor(private val repository: ProposalRepository) : ViewModel() {
    private val _state = MutableStateFlow(ProposalUiState())
    val state = _state.asStateFlow()

    fun onEvent(event: ProposalEvent) {
        when (event) {
            is ProposalEvent.AmountChanged -> {
                _state.update { it.copy(amount = event.amount) }
            }

            is ProposalEvent.CvChanged -> {
                _state.update { it.copy(cv = event.cv) }
            }

            is ProposalEvent.Submit -> {
                submitProposal()
            }
        }

    }

    private fun submitProposal() {
        viewModelScope.launch {
            val id = _state.value.jobId
            val amount = _state.value.amount.toInt()
            val cv = _state.value.cv
            val userId = _state.value.userId
            val request = ProposalRequest(
                amount = amount,
                cv = cv,
                userId = userId,
                jobId = id
            )

            when (val response = repository.createProposal(id, request)) {
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            error = response.message ?: "An unknown error occurred",
                            isLoading = false
                        )

                    }
                }

                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            navigateToHome = true,
                            success = response.message ?: "Proposal submitted successfully",
                            isLoading = false
                        )
                    }
                }

                is Resource.Loading -> {}
            }
        }

    }

}

data class ProposalUiState(
    val amount: String = "",
    val cv: String = "",
    val userId: Int = 0,
    val jobId: Int = 0,
    val navigateToHome: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = "",
    val success: String = ""
)

sealed class ProposalEvent {
    data class AmountChanged(val amount: String) : ProposalEvent()
    data class CvChanged(val cv: String) : ProposalEvent()
    data object Submit : ProposalEvent()
}

/***
 *   "amount": 0,
 *   "cv": "string",
 *   "user_id": 0,
 *   "job_id": 0
 */

@Preview(showBackground = true)
@Composable
private fun FormPrev() {
    Surface {
        KaziHubTheme {
            ProposalRequestForm(
                state = ProposalUiState(),
                event = {}
            )
        }
    }
}