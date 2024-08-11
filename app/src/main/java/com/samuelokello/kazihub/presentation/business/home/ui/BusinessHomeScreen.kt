package com.samuelokello.kazihub.presentation.business.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.presentation.common.components.AppBar
import com.samuelokello.kazihub.presentation.destinations.CreateJobUiDestination
import com.samuelokello.kazihub.ui.theme.KaziHubTheme

@Composable
fun BusinessHomeScreen(
    businessViewModel: BusinessHomeViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val jobs by businessViewModel.jobs.collectAsState()
    val isLoading by businessViewModel.isLoading.collectAsState()

    Scaffold(
        topBar = { AppBar(onNavigationIconClick = {}) },

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> CircularProgressIndicator()
                else -> BusinessHomeScreenContent(
                    jobs = jobs,
                    navigateToCreateJob = { navigator.navigate(CreateJobUiDestination) },
                    onBusinessUiEvent = businessViewModel::businessHomeEvent
                )
            }
        }
    }
}

@Preview
@Composable
fun BusinessHomeScreenPreview() {
    KaziHubTheme {
//        BusinessHomeScreen()
    }
}