package com.samuelokello.kazihub.presentation.business.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.samuelokello.kazihub.R
import com.samuelokello.kazihub.domain.model.job.data
import com.samuelokello.kazihub.presentation.business.home.components.JobListItem
import com.samuelokello.kazihub.presentation.business.home.components.LineGraph
import com.samuelokello.kazihub.presentation.business.home.components.StatsCard
import com.samuelokello.kazihub.presentation.business.home.event.BusinessHomeUiEvents
import com.samuelokello.kazihub.presentation.business.home.state.BusinessHomeUiState
import com.samuelokello.kazihub.presentation.common.components.NoJobsMessage
import com.samuelokello.kazihub.ui.theme.primaryLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessHomeScreenContent(
    jobs: List<data> = emptyList(),
    navigateToCreateJob: () -> Unit,
    onBusinessUiEvent: (BusinessHomeUiEvents) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier =
                Modifier
                    .padding(end = 16.dp, bottom = 92.dp)
                    .size(64.dp),
                onClick = navigateToCreateJob,
                contentColor = Color.White,
                containerColor = primaryLight,
            ) {
                Icon(Icons.Default.Add, contentDescription = "Create data")
            }
        },
    ) { paddingValues ->

        Column(
            modifier =
            Modifier
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Column {
                Text(text = "Hello 👋 !", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                Row {
                    StatsCard(
                        statIcon = { Icon(Icons.Default.ShoppingBag, contentDescription = null) },
                        title = "Total Expenditure",
                        value = "Ksh 0",
                    )
                    Spacer(modifier = Modifier.size(32.dp))
                    StatsCard(
                        statIcon = { Icon(Icons.Default.Work, contentDescription = null) },
                        title = "Jobs Posted",
                        value = " 0",
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                Text(
                    text = "Expenditure Over Time",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
                Spacer(Modifier.height(16.dp))
                LineGraph(jobs)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                Text(
                    text = "Recent Activities",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            if (jobs.isEmpty()) {
                NoJobsMessage(R.string.click_the_button_to_create_your_first_job)
            } else {
                LazyColumn {
                    items(jobs) { job ->
                        JobListItem(
                            data = job,
                            onJobClick = {
                                job.id?.let { jobId ->
                                    onBusinessUiEvent(BusinessHomeUiEvents.OnJobClick(jobId))
                                }
                            },
                        )
                    }
                }
            }
        }
    }
}

