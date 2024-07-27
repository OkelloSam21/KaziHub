package com.samuelokello.kazihub.presentation.business.Home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.samuelokello.kazihub.domain.model.job.Job
import com.samuelokello.kazihub.domain.model.job.category.CategoryResponse
import com.samuelokello.kazihub.presentation.business.Home.BusinessHomeUiEvents
import com.samuelokello.kazihub.presentation.common.components.CustomButton
import com.samuelokello.kazihub.presentation.common.components.LocationAutocompleteTextField
import com.samuelokello.kazihub.presentation.shared.components.EditTextField
import com.samuelokello.kazihub.ui.theme.primaryLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessHomeScreenContent(
    jobs: List<Job> = emptyList(),
    createJobState: CreateJobSheetState,
    showSheet: Boolean = false,
    onSheetDismissRequest: () -> Unit,
    onBusinessUiEvent: (BusinessHomeUiEvents) -> Unit,
    onCreateJobUiEvent: (CreateJobUiEvent) -> Unit,
    categories: List<CategoryResponse> = emptyList(),
    selectedCategory: CategoryResponse,
) {
    // Create a SheetState object
    val sheetState = rememberModalBottomSheetState()

    // Update the SheetState object based on the showSheet value
    LaunchedEffect(showSheet) {
        if (showSheet) {
            sheetState.expand()
        } else {
            sheetState.hide()
        }
    }

    if (showSheet) {
        CreateJobUi(
            createJobState = createJobState,
            onCreateJobUiEvent = onCreateJobUiEvent,
            categories = categories,
            selectedCategory = selectedCategory,
        )
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier =
                Modifier
                    .padding(end = 16.dp, bottom = 92.dp)
                    .size(64.dp),
                onClick = { onBusinessUiEvent(BusinessHomeUiEvents.OnFABClick) },
                contentColor = Color.White,
                containerColor = primaryLight,
            ) {
                Icon(Icons.Default.Add, contentDescription = "Create Job")
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
                Text(text = "Hello 👋", style = MaterialTheme.typography.titleMedium)
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
                NoJobsMessage()
            } else {
                LazyColumn {
                    items(jobs) { job ->
                        JobListItem(
                            job = job,
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

@Composable
fun NoJobsMessage() {
    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = Color.Gray,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No jobs posted yet",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Click the '+' button to create your first job.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun CreateJobUi(
    createJobState: CreateJobSheetState,
    onCreateJobUiEvent: (CreateJobUiEvent) -> Unit,
    categories: List<CategoryResponse> = emptyList(),
    selectedCategory: CategoryResponse,
) {
    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(bottom = 48.dp),
    ) {
        Text(text = "Create Job", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        EditTextField(
            value = createJobState.title,
            onValueChange = { onCreateJobUiEvent(CreateJobUiEvent.OnTitleChange(it)) },
            label = "Title",
            singleLine = true,
            keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))

        EditTextField(
            value = createJobState.description,
            onValueChange = { onCreateJobUiEvent(CreateJobUiEvent.OnDescriptionChange(it)) },
            label = "Description",
            singleLine = true,
            keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))

        EditTextField(
            value = createJobState.budget.toString(),
            onValueChange = { onCreateJobUiEvent(CreateJobUiEvent.OnBudgetChange(it.toInt())) },
            label = "Budget",
            singleLine = true,
            keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))

        LocationAutocompleteTextField(
            value = createJobState.location,
            onValueChange = { onCreateJobUiEvent(CreateJobUiEvent.OnLocationChange(it)) },
            suggestions = emptyList(),
            onSuggestionSelected = {},
            placeholder = "Location",
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))

        EditTextField(
            value = createJobState.qualifications.joinToString(", "),
            onValueChange = { onCreateJobUiEvent(CreateJobUiEvent.OnQualificationsChange(it)) },
            label = "Qualifications",
            singleLine = true,
            keyboardOptions =
            KeyboardOptions(
                imeAction = ImeAction.Done,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))

        CategoryDropDown(
            categories = categories,
            selectedCategory = selectedCategory,
            onCreateJobUiEvent = onCreateJobUiEvent,
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(
            onClick = {
                // Handle job creation logic
                onCreateJobUiEvent(
                    CreateJobUiEvent.OnCreateJobClick(
                        title = createJobState.title,
                        description = createJobState.description,
                        budget = createJobState.budget,
                        category = createJobState.category,
                        location = createJobState.location,
                        qualifications = createJobState.qualifications.toString(),
                    ),
                )
            },
            isEnabled =
            createJobState.title.isNotBlank() &&
                    createJobState.description.isNotBlank() &&
                    createJobState.budget.toString().isNotBlank() &&
                    createJobState.location.isNotBlank() &&
                    createJobState.qualifications.toString().isNotBlank(),
            text = "Create Job",
        )
    }
}

@Composable
fun CategoryDropDown(
    categories: List<CategoryResponse?>,
    selectedCategory: CategoryResponse?,
    onCreateJobUiEvent: (CreateJobUiEvent) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val icon = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown

    Box(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        OutlinedTextField(
            value = selectedCategory?.name ?: "",
            onValueChange = {},
            label = { Text("Category") },
            singleLine = true,
            keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.clickable { expanded = !expanded },
                )
            },
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(),
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    onClick = {
                        if (category != null) {
                            onCreateJobUiEvent(
                                CreateJobUiEvent.OnCategoryChange(
                                    category.name ?: ""
                                )
                            )
                        }
                        expanded = false
                    },
                    text = {
                        if (category != null) {
                            category.name?.let { Text(text = it) }
                        }
                    },
                )
            }
        }
    }
}
