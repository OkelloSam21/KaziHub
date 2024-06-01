package com.samuelokello.kazihub.presentation.business.Home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.samuelokello.kazihub.domain.model.job.Category
import com.samuelokello.kazihub.presentation.business.Home.BusinessHomeUiEvents
import com.samuelokello.kazihub.presentation.common.components.AppBar
import com.samuelokello.kazihub.presentation.common.components.CustomButton
import com.samuelokello.kazihub.presentation.common.components.EditTextField
import com.samuelokello.kazihub.presentation.common.components.LocationAutocompleteTextField
import com.samuelokello.kazihub.presentation.worker.data.Job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessHomeScreenContent(
    jobs: List<Job>,
    onEvent: (BusinessHomeUiEvents) -> Unit,
    showModalSheet: Boolean = false,
    setShowModalSheet: (Boolean) -> Unit
) {

    if (showModalSheet) {
        ModalBottomSheet(onDismissRequest = { setShowModalSheet(false) }) {
            CreateJobSheet(onEvent, setShowModalSheet)
        }
    }

    Scaffold(
        topBar = {
            AppBar(onNavigationIconClick = { onEvent(BusinessHomeUiEvents.OnDrawerClick) })
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(end = 16.dp, bottom = 92.dp),
                onClick = { setShowModalSheet(true) }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Create Job")
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Column {
                Text( text = "Hello 👋")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                Text(
                    text = "JOB BUDGET OVER TIME",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Spacer(Modifier.height(16.dp))
                LineGraph(jobs)
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (jobs.isEmpty()) {
                NoJobsMessage()
            } else {
                LazyColumn {
                    items(jobs) { job ->
                        JobListItem(
                            job = job,
                            onJobClick = {
                                job.id?.let { jobId ->
                                    onEvent(BusinessHomeUiEvents.OnJobClick(jobId))
                                }
                            }
                        )
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
            .padding(16.dp),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateJobSheet(
    onEvent: (CreateJobUiEvent) -> Unit,
    state: CreateJobSheetState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Create Job", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        EditTextField(
            value = state.title,
            onValueChange = {onEvent(CreateJobUiEvent.OnTitleChange(it))},
            label = "Title",
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        EditTextField(
            value = state.description,
            onValueChange = {onEvent(CreateJobUiEvent.OnDescriptionChange(it))},
            label = "Description",
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        EditTextField(
            value = state.budget,
            onValueChange = {onEvent(CreateJobUiEvent.OnBudgetChange(it)) },
            label = "Budget",
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        LocationAutocompleteTextField(
            value = state.location,
            onValueChange = {onEvent(CreateJobUiEvent.OnLocationChange(it))},
            suggestions = emptyList(),
            onSuggestionSelected = {},
            placeholder = "Location",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        EditTextField(
            value = state.qualification.toString(),
            onValueChange = {onEvent(CreateJobUiEvent.OnQualificationsChange(it))},
            label = "Qualifications",
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(
            onClick = {
                // Handle job creation logic
                onEvent(BusinessHomeUiEvents.OnCreateJobClick(
                    title = state.title,
                    description = state.description,
                    budget = state.budget,
                    category = state.category,
                    location = state.location,
                    qualifications = state.qualification
                ))
                setShowModalSheet(false)
            },
            isEnabled = title.isNotBlank() && description.isNotBlank(),
            text = "Create Job"
        )
    }
}
