package com.samuelokello.kazihub.presentation.business

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.domain.model.proposal.proposalResponse.ProposalData
import com.samuelokello.kazihub.ui.theme.KaziHubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun ProposalUi(
    proposalId: Int,
    viewModel: ProposalViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(proposalId) {
        viewModel.fetchProposalById(proposalId)
        Log.d("ProposalUi", "LaunchedEffect: $proposalId")
    }

    KaziHubTheme {
        Surface {
            Column(Modifier.fillMaxSize()) {
                CenterAlignedTopAppBar(
                    title = { Text(text = "Proposal") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.popBackStack() }) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                        }
                    }
                )

                when {
                    state.isLoading -> {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                    state.error != null -> {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Text(
                                "Error: ${state.error}",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                    state.proposals.isEmpty() -> {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Text(
                                "No proposal found.",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                    else -> {
                        LazyColumn {
                            items(state.proposals) { proposal ->
                                ProposalItem(proposal)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProposalItem(
    proposal: ProposalData,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = "Amount: ${proposal.amount}", style = MaterialTheme.typography.bodyLarge)
            Text(
                text = "Status: ${proposal.status.value}",
                style = MaterialTheme.typography.bodyMedium
            )
            // Add more Text composables to display other proposal details as needed
        }
        Row {
            Button(onClick = { /* Handle accept logic */ }, modifier = Modifier.padding(end = 8.dp)) {
                Text(text = "Accept")
            }
            Button(onClick = { /* Handle reject logic */ }) {
                Text(text = "Reject")
            }
        }
    }
}

@Preview
@Composable
private fun ProposalUiPreview() {
    KaziHubTheme {

    }

}
