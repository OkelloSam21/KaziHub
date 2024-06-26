package com.samuelokello.kazihub.presentation.worker.ui.home

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.domain.model.job.WorkerHomeScreenUiState
import com.samuelokello.kazihub.presentation.common.components.AppBar
import com.samuelokello.kazihub.presentation.common.components.JobCard
import com.samuelokello.kazihub.presentation.worker.data.HomeViewModel
import com.samuelokello.kazihub.domain.model.job.Job
import com.samuelokello.kazihub.presentation.worker.state.WorkerHomeScreenUiEvent
import com.samuelokello.kazihub.ui.theme.KaziHubTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Worker Home Screen
 * A composable function that displays the worker home screen
 * It contains a search bar, popular data, and recent posts
 * */

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

                        }

                        is WorkerHomeScreenUiEvent.NavigateToJobDetails -> {

                        }

                        WorkerHomeScreenUiEvent.OnSHowAllClick -> {
//                            navigator.navigate()
                        }

                        WorkerHomeScreenUiEvent.OnSearchClick -> {
//                            navigator.navigate()
                        }

                        WorkerHomeScreenUiEvent.OnViewAllClick -> {
                            TODO()
                        }

                        WorkerHomeScreenUiEvent.OpenDrawer -> {

                        }

                        is WorkerHomeScreenUiEvent.SearchQueryChanged -> TODO()
                        WorkerHomeScreenUiEvent.OnPullToRefresh -> {
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
    val recentJobs = state.recentJobs
    val popularJobs = state.nearByJobs
    val isRefreshing = state.isRefreshing
    val pullRefreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = { event(WorkerHomeScreenUiEvent.OnPullToRefresh) }
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(state = pullRefreshState)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppBar {
                scope.launch {
                    state.drawerState
                }
            }

            Column(
                modifier = Modifier
                    .padding(top = 32.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            ) {
                if (state.isLoading && state.nearByJobs.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                } else {
                    Column {

                        Column {
                            Row {
                                Text(
                                    text = "Near By Jobs",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )

                                Spacer(modifier = Modifier.weight(2f))
                                TextButton(onClick = { event(WorkerHomeScreenUiEvent.OnViewAllClick) }) {
                                    Text(text = "View All")
                                }
                            }

                            Log.d(
                                "WorkerHomeScreenContent",
                                "popularJobs size: ${popularJobs.size}"
                            )

                            LazyRow {
                                items(popularJobs) { job ->
                                    if (job != null) {
                                        JobCard(job = job)
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column {
                        Row {
                            Text(text = "Recent Jobs")
                            Spacer(modifier = Modifier.weight(2f))
                            TextButton(onClick = { event(WorkerHomeScreenUiEvent.OnViewAllClick) }) {
                                Text(text = "Show All")
                            }
                        }

                        Log.d("WorkerHomeScreenContent", "recentJobs size: ${recentJobs.size}")
                        LazyColumn {

                            items(recentJobs) { job ->
                                RecentPost(
                                    job = job,
                                    onEvent = event
                                )
                            }
                        }
                    }

                }
            }
        }

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}


/**
 * Recent Post
 * A composable function that displays a recent post
 * */

@Composable
fun RecentPost(
    job: Job,
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
        ),
        content = {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(32.dp)
                ) {
//                    AsyncImage(
//                        model = ImageRequest.Builder(LocalContext.current)
//                            .data(job.imageUrl)
//                            .crossfade(true)
//                            .build(),
//                        placeholder = painterResource(id = R.drawable.icons8_google_48),
//                        error = painterResource(id = R.drawable.icons8_google_48),
//                        contentScale = ContentScale.Crop,
//                        contentDescription = null,
//                        modifier = modifier
//                            .size(40.dp)
//                    )
                    Column {
                        Text(text = job.title.toString())
                        Text(text = job.location.toString())
                    }

                    Text(text = "ksh ${job.budget}")
                }
            }
        }
    )
}

