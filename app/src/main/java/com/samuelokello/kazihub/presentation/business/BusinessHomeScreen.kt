package com.samuelokello.kazihub.presentation.business

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.samuelokello.kazihub.presentation.shared.components.AppBar
import com.samuelokello.kazihub.presentation.worker.data.Job
import com.samuelokello.kazihub.ui.theme.KaziHubTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BusinessHomeScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    Scaffold (
        topBar = {
            AppBar(onNavigationIconClick = {})
        },
        modifier = Modifier.padding(bottom = 32.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(text = "Business Home Screen")
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

@Composable
fun LineGraph(jobs: List<Job>) {
    val maxValue = jobs.maxOfOrNull { it.budget } ?: 1f
    val minValue = jobs.minOfOrNull { it.budget } ?: 0f
    val graphHeight = 200.dp
    val graphWidth = 300.dp

    Canvas(modifier = Modifier.size(graphWidth, graphHeight)) {
        val widthPerPoint = size.width / (jobs.size - 1)
        val heightPerPoint = size.height / (maxValue - minValue)

        for (i in 0 until jobs.size - 1) {
            val start = Offset(i * widthPerPoint, size.height - jobs[i].budget * heightPerPoint)
            val end = Offset((i + 1) * widthPerPoint, size.height - jobs[i + 1].budget * heightPerPoint)
            drawLine(Color.Blue, start, end)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessHomeScreenContent(
    businessName: String,
    jobs: List<Job>,
    onJobClick: (Job) -> Unit,
    onCreateJobClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = businessName) },
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    scrolledContainerColor = MaterialTheme.colorScheme.onSurface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateJobClick) {
                Icon(Icons.Default.Add, contentDescription = "Create Job")
            }
        }
    ) {paddingValues ->
        Column (modifier = Modifier.padding(paddingValues)){
            LineGraph(jobs)
            LazyColumn {
                items(jobs) { job ->
                    JobListItem(job = job, onJobClick = onJobClick)
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