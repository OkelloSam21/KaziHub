package com.samuelokello.kazihub.presentation.worker.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.samuelokello.kazihub.R
import com.samuelokello.kazihub.presentation.shared.components.AppBar
import com.samuelokello.kazihub.presentation.shared.components.JobCard
import com.samuelokello.kazihub.presentation.worker.data.HomeViewModel
import com.samuelokello.kazihub.presentation.worker.data.Job
import com.samuelokello.kazihub.presentation.worker.data.WorkerHomeScreenUiState
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
fun WorkerHomeScreen() {
    val viewModel: HomeViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()

    Surface {
        KaziHubTheme {
            WorkerHomeScreenContent(state = state, scope = scope)
        }
    }

}

@Composable
fun WorkerHomeScreenContent(
    state: State<WorkerHomeScreenUiState>,
    scope: CoroutineScope
) {
    Column {
        AppBar {
            scope.launch {
                state.value.drawerState
            }
        }
        Column(
            modifier = Modifier
                .padding(top = 32.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth()
//                .pullRefresh(),
        ) {
            if (state.value.isLoading) {
                Box(modifier = Modifier.fillMaxSize()){
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            } else {
                Column {
                    Column {
                        Row {
                            Text(
                                text = "Popular Jobs",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            Spacer(modifier = Modifier.weight(2f))
                            TextButton(onClick = { /*TODO*/ }) {
                                Text(text = "View All")
                            }
                        }
                    }
                    Column {
                        Text(text = "")
                        LazyRow {
                            items(state.value.data.size) { job ->
                                state.value.data[job]?.let { it1 -> JobCard(job = it1) }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Column {
                        Text(text = "Recent Posts")
                        LazyColumn {

                            items(state.value.data) { job ->
                                if (job != null) {
                                    RecentPost(job)
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}

/**
 * Recent Post
 * A composable function that displays a recent post
 * */

@Composable
fun RecentPost(job: Job) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
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
                    Image(
                        painter = painterResource(id = R.drawable.icons8_google_48),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )

                    Column {
                        Text(text = job.title ?: "")
                        Text(text = job.time ?: "")
                    }

                    Text(text = "ksh ${job.budget ?: 0}")
                }
            }
        }
    )
}

