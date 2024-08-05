package com.samuelokello.kazihub.presentation.worker.ui.job

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.presentation.common.components.CustomButton
import com.samuelokello.kazihub.presentation.destinations.HomeScreenDestination

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun JobDetailsScreen(
    jobId: Int,
    viewModel: JobDetailsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    LaunchedEffect(key1 = true) {
        viewModel.fetchJobById(jobId)
    }

    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        CenterAlignedTopAppBar(
            title = { Text(text = "Job Details") },
            navigationIcon = {
                IconButton(onClick =
                {navigator.popBackStack(HomeScreenDestination.route, inclusive = false)}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Gray
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = state.job.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = state.job.business.email,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Location and Salary
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location",
                tint = Color.Gray
            )
            Text(
                text = state.job.location ?: "",
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Ksh " + state.job.budget.toString(),
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Text(
            text = "Job Description",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = state.job.description ?: "",
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Qualifications
        Text(
            text = "Qualifications",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        state.job.qualifications.forEach {
            Text(
                text = it.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Light
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Category
        Text(
            text = "Category",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = state.job.category.name ?: "",
            modifier = Modifier.padding(top = 4.dp)
        )


        Spacer(modifier = Modifier.height(32.dp))

        // Apply Now Button
        CustomButton(
            onClick = { /* Handle apply action */ },
            text = "Apply Now",
            isEnabled = true,
        )
    }
}
