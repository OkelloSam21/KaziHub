package com.samuelokello.kazihub.presentation.business.Home

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samuelokello.kazihub.presentation.shared.components.AppBar
import com.samuelokello.kazihub.presentation.worker.data.Job
import com.samuelokello.kazihub.ui.theme.KaziHubTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BusinessHomeScreen() {
//    val navController = rememberNavController()
//    val drawerState = rememberDrawerState(DrawerValue.Closed)
    Scaffold(
        topBar = {
            AppBar(onNavigationIconClick = {})
        },
        modifier = Modifier.padding(bottom = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            BusinessHomeScreenContent(
                jobs = emptyList(),
                onEvent = {
                    when(it) {
                        BusinessHomeUiEvents.OnCreateJobClick -> {}
                        BusinessHomeUiEvents.OnDrawerClick -> {}
                        is BusinessHomeUiEvents.OnJobClick -> {}
                    }
                }
            )
        }
    }
}

@Composable
fun JobListItem(job: Job, onJobClick: (Job) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onJobClick(job) },
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = job.title.toString(), style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = job.desc.toString(), style = MaterialTheme.typography.bodyLarge)
        }
    }
}

/**
 *  composable function that draws a line graph
 *  @param jobs list of jobs fetched from kazi hub api
 */
@Composable
fun LineGraph(jobs: List<Job>) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "JOB BUDGET OVER TIME",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Define graph dimensions
        val graphHeight = 200.dp
        val graphWidth = 300.dp
        val padding = 8.dp

        // Extract non-null budgets, or use 0 if the budget is null
        val budgets = jobs.map { it.budget ?: 0 }
        val maxValue = budgets.maxOrNull() ?: 1
        val minValue = budgets.minOrNull() ?: 0

        Canvas(
            modifier = Modifier
                .size(graphWidth, graphHeight)
                .padding(padding)
        ) {
            val paddingPx = padding.toPx()
            val widthPerPoint =
                if (jobs.size > 1) (size.width - paddingPx * 2) / (jobs.size - 1) else 0f
            val heightPerPoint =
                if (maxValue != minValue) (size.height - paddingPx * 2) / (maxValue - minValue) else 1f

            // Draw axes
            drawLine(
                Color.Gray,
                Offset(paddingPx, paddingPx),
                Offset(paddingPx, size.height - paddingPx)
            )
            drawLine(
                Color.Gray,
                Offset(paddingPx, size.height - paddingPx),
                Offset(size.width - paddingPx, size.height - paddingPx)
            )

            if (jobs.isNotEmpty()) {
                for (i in 0 until jobs.size - 1) {
                    val startBudget = jobs[i].budget ?: 0
                    val endBudget = jobs[i + 1].budget ?: 0

                    val start = Offset(
                        paddingPx + i * widthPerPoint,
                        size.height - paddingPx - (startBudget - minValue) * heightPerPoint
                    )
                    val end = Offset(
                        paddingPx + (i + 1) * widthPerPoint,
                        size.height - paddingPx - (endBudget - minValue) * heightPerPoint
                    )
                    drawLine(Color.Blue, start, end, strokeWidth = 4f)
                }

                // Draw circles at each data point
                jobs.forEachIndexed { index, job ->
                    val budget = job.budget ?: 0
                    val point = Offset(
                        paddingPx + index * widthPerPoint,
                        size.height - paddingPx - (budget - minValue) * heightPerPoint
                    )
                    drawCircle(Color.Red, radius = 6f, center = point)
                }

                // if jobs is empty add text place holder inside the graph

            }
        }

    }

}


@Composable
fun BusinessHomeScreenContent(
    jobs: List<Job>,
    onEvent: (BusinessHomeUiEvents) -> Unit
) {
    Scaffold(
        topBar = {
            AppBar(onNavigationIconClick = { onEvent(BusinessHomeUiEvents.OnDrawerClick)})
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {onEvent(BusinessHomeUiEvents.OnCreateJobClick)} ) {
                Icon(Icons.Default.Add, contentDescription = "Create Job")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Spacer(modifier = Modifier.height(32.dp))
            Column (
                modifier = Modifier.padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                LineGraph(jobs)
            }

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

@Preview
@Composable
fun BusinessHomeScreenPreview() {
    KaziHubTheme {
        BusinessHomeScreen()
    }
}