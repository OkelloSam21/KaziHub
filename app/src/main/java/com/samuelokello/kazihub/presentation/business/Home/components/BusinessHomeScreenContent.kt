package com.samuelokello.kazihub.presentation.business.Home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.samuelokello.kazihub.R
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
            FloatingActionButton(onClick = { onEvent(BusinessHomeUiEvents.OnCreateJobClick) }) {
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
            Column {
                if (jobs.isEmpty()) {
                    Image(
                        painter = painterResource(id = R.drawable.worker),
                        contentDescription = null
                    )
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