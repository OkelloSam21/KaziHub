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
//    val navController = rememberNavController()
//    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val showBottomSheet by rememberSaveable { mutableStateOf(false) }
    val jobs by viewModel.jobs.collectAsState()
    Scaffold(
        topBar = { AppBar(onNavigationIconClick = {}) },
//        modifier = Modifier.padding(bottom = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            BusinessHomeScreenContent(
                jobs = jobs.jobs,
                onEvent = {
                    when(it) {
                        BusinessHomeUiEvents.OnFABClick -> { !showBottomSheet}
                        BusinessHomeUiEvents.OnDrawerClick -> {}
                        is BusinessHomeUiEvents.OnJobClick -> {}
                    }
                },
                showModalSheet = showBottomSheet,
                createJobEvent = {
                    when(it) {
                        is CreateJobUiEvent.OnCreateJobClick-> {}
                        is CreateJobUiEvent.OnBudgetChange -> TODO()
                        is CreateJobUiEvent.OnDescriptionChange -> TODO()
                        is CreateJobUiEvent.OnLocationChange -> TODO()
                        is CreateJobUiEvent.OnQualificationsChange -> TODO()
                        is CreateJobUiEvent.OnTitleChange -> {}
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