package com.samuelokello.kazihub.presentation.business.Home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.samuelokello.kazihub.presentation.business.Home.components.BusinessHomeScreenContent
import com.samuelokello.kazihub.presentation.business.Home.components.CreateJobUiEvent
import com.samuelokello.kazihub.presentation.business.Home.state.BusinessHomeViewModel
import com.samuelokello.kazihub.presentation.common.components.AppBar
import com.samuelokello.kazihub.ui.theme.KaziHubTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BusinessHomeScreen(viewModel: BusinessHomeViewModel = hiltViewModel()) {

    val showBottomSheet by rememberSaveable { mutableStateOf(false) }
    val jobs by viewModel.jobs.collectAsState()
    Scaffold(
        topBar = { AppBar(onNavigationIconClick = {}) },
//        modifier = Modifier.padding(bottom = 32.dp)
    ) { event ->
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            BusinessHomeScreenContent(
                jobs = jobs.jobs,
                onEvent = {
                    when (it) {
                        BusinessHomeUiEvents.OnFABClick -> { !showBottomSheet }
                        BusinessHomeUiEvents.OnDrawerClick -> {}
                        is BusinessHomeUiEvents.OnJobClick -> {}
                    }
                },
                createJobEvent = {event ->
                    when (event) {
                        is CreateJobUiEvent.OnCreateJobClick -> viewModel.onCreateJobClick()
                        is CreateJobUiEvent.OnBudgetChange -> viewModel.onBudgetChange(event.budget)
                        is CreateJobUiEvent.OnDescriptionChange -> viewModel.onDescriptionChange(event.description)
                        is CreateJobUiEvent.OnLocationChange -> viewModel.onLocationChange(event.location)
                        is CreateJobUiEvent.OnQualificationsChange -> viewModel.onQualificationsChange(event.qualifications)
                        is CreateJobUiEvent.OnTitleChange -> viewModel.onTitleChange(event.title)
                    }
                }
            )
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