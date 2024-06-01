package com.samuelokello.kazihub.presentation.business.Home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.samuelokello.kazihub.presentation.business.Home.BusinessHomeUiEvents
import com.samuelokello.kazihub.presentation.shared.components.AppBar
import com.samuelokello.kazihub.presentation.worker.data.Job

@Composable
fun BusinessHomeScreenContent(
    jobs: List<Job>,
    onEvent: (BusinessHomeUiEvents) -> Unit
) {
    Scaffold(
        topBar = {
            AppBar(onNavigationIconClick = { onEvent(BusinessHomeUiEvents.OnDrawerClick) })
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(end = 16.dp, bottom = 32.dp),
                onClick = { onEvent(BusinessHomeUiEvents.OnCreateJobClick) }
            ) {

                Icon(Icons.Default.Add, contentDescription = "Create Job")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier.padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LineGraph(jobs)
            }

            Spacer(modifier = Modifier.height(16.dp))
            Column {

                if (jobs.isEmpty()) {
                    NoJobsMessage()
                }

                if (jobs.isEmpty()) {
                    LazyColumn {
                        items(jobs) { job ->
                            JobListItem(
                                job = job,
                                onJobClick = {
                                    job.id?.let { it1 ->
                                        BusinessHomeUiEvents.OnJobClick(
                                            it1
                                        )
                                    }?.let { it2 -> onEvent(it2) }
                                }
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun NoJobsMessage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No jobs posted yet",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Click the '+' button to create your first job.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

