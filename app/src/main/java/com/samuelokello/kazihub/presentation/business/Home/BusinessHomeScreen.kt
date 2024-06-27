package com.samuelokello.kazihub.presentation.business.Home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.samuelokello.kazihub.domain.model.job.category.CategoryResponse
import com.samuelokello.kazihub.presentation.business.Home.components.BusinessHomeScreenContent
import com.samuelokello.kazihub.presentation.business.Home.components.CreateJobUiEvent
import com.samuelokello.kazihub.presentation.business.Home.state.BusinessHomeViewModel
import com.samuelokello.kazihub.presentation.business.Home.state.CreateJobViewModel
import com.samuelokello.kazihub.presentation.common.components.AppBar
import com.samuelokello.kazihub.ui.theme.KaziHubTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BusinessHomeScreen(
    businessViewModel: BusinessHomeViewModel = hiltViewModel(),
    jobViewModel: CreateJobViewModel = hiltViewModel()
) {

    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    val jobs by businessViewModel.jobs.collectAsState()
    val createJobState by jobViewModel.createJobState.collectAsState()
    val selectedCategory by remember { mutableStateOf<CategoryResponse?>(null) }
    val categories by jobViewModel.categories.collectAsState()

    Scaffold(
        topBar = { AppBar(onNavigationIconClick = {}) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            BusinessHomeScreenContent(
                jobs = jobs,
                createJobState = createJobState,
                showSheet = showBottomSheet,
                onSheetDismissRequest = { showBottomSheet = false },
                onBusinessUiEvent = { businessUiEvent ->
                    when(businessUiEvent) {
                        BusinessHomeUiEvents.OnFABClick -> { showBottomSheet = !showBottomSheet }
                        BusinessHomeUiEvents.OnDrawerClick -> {}
                        is BusinessHomeUiEvents.OnJobClick -> {}
                    }
                },
                onCreateJobUiEvent = { createJobUiEvent ->
                    when(createJobUiEvent) {
                        is CreateJobUiEvent.OnCreateJobClick -> jobViewModel.onCreateJobClick()
                        is CreateJobUiEvent.OnBudgetChange -> jobViewModel.onBudgetChange(createJobUiEvent.budget)
                        is CreateJobUiEvent.OnDescriptionChange -> jobViewModel.onDescriptionChange(createJobUiEvent.description)
                        is CreateJobUiEvent.OnLocationChange -> jobViewModel.onLocationChange(createJobUiEvent.location)
                        is CreateJobUiEvent.OnQualificationsChange -> jobViewModel.onQualificationsChange(createJobUiEvent.qualifications)
                        is CreateJobUiEvent.OnTitleChange -> jobViewModel.onTitleChange(createJobUiEvent.title)
                        is CreateJobUiEvent.OnCategoryChange -> { jobViewModel.onCategoryChange(createJobUiEvent.item) }
                    }
                },
                categories = categories,
                selectedCategory = selectedCategory
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