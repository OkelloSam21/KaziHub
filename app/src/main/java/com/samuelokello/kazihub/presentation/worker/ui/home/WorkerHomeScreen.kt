package com.samuelokello.kazihub.presentation.worker.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.R
import com.samuelokello.kazihub.domain.model.job.WorkerHomeScreenUiState
import com.samuelokello.kazihub.domain.model.job.data
import com.samuelokello.kazihub.presentation.common.components.AppBar
import com.samuelokello.kazihub.presentation.common.components.JobCard
import com.samuelokello.kazihub.presentation.common.components.NoJobsMessage
import com.samuelokello.kazihub.presentation.destinations.JobDetailsScreenDestination
import com.samuelokello.kazihub.presentation.worker.data.HomeViewModel
import com.samuelokello.kazihub.presentation.worker.state.WorkerHomeScreenUiEvent
import com.samuelokello.kazihub.ui.theme.KaziHubTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WorkerHomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val homeUiState by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()

    Surface {
        KaziHubTheme {
            WorkerHomeScreenContent(
                state = homeUiState,
                scope = scope,
                event = { event ->
                    when (event) {
                        is WorkerHomeScreenUiEvent.JobSelected -> {
                            navigator.navigate(JobDetailsScreenDestination(event.data.id ?: 0))
                        }
                        is WorkerHomeScreenUiEvent.OnViewAllClick -> { }
                        is WorkerHomeScreenUiEvent.OpenDrawer ->{}
                        is WorkerHomeScreenUiEvent.OnPullToRefresh -> {
                            viewModel.refreshAllData()
                        }
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WorkerHomeScreenContent(
    state: WorkerHomeScreenUiState,
    scope: CoroutineScope,
    event: (WorkerHomeScreenUiEvent) -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = { event(WorkerHomeScreenUiEvent.OnPullToRefresh) }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(
                    state = pullRefreshState,
                    enabled = true
                )
        ) {
            AppBar { scope.launch { state.drawerState } }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                if (state.isLoading) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                } else {
                    JobSection(
                        title = "Near By Jobs",
                        jobs = state.nearByJobs,
                        onViewAllClick = { event(WorkerHomeScreenUiEvent.OnViewAllClick) },
                        onJobClick = { job -> event(WorkerHomeScreenUiEvent.JobSelected(job)) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    JobSection(
                        title = "Recent Jobs",
                        jobs = state.recentJobs,
                        onViewAllClick = { event(WorkerHomeScreenUiEvent.OnViewAllClick) },
                        onJobClick = { job -> event(WorkerHomeScreenUiEvent.JobSelected(job)) }
                    )
                }
            }
        }

        PullRefreshIndicator(
            refreshing = state.isRefreshing,
            state = pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun JobSection(
    title: String,
    jobs: List<data?>,
    onViewAllClick: () -> Unit,
    onJobClick: (data) -> Unit
) {
    Column {
        Row {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = onViewAllClick) {
                Text(text = "View All")
            }
        }

        if (jobs.isEmpty()) {
            NoJobsMessage(R.string.no_jobs_message)
        } else {
            LazyRow {
                items(jobs) { job ->
                    job?.let {
                        JobCard(job = it) { onJobClick(it) }
                    }
                }
            }
        }
    }
}

@Composable
fun RecentPost(
    job: data,
    onEvent: (WorkerHomeScreenUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onEvent(WorkerHomeScreenUiEvent.JobSelected(job)) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Column {
                Text(text = job.title ?: "")
                Text(text = job.location ?: "")
            }
            Text(text = "ksh ${job.budget}")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WorkerHomePreview() {
    Surface {
        KaziHubTheme {
            WorkerHomeScreenContent(
                state = WorkerHomeScreenUiState(),
                scope = rememberCoroutineScope(),
                event = {}
            )
        }
    }
}