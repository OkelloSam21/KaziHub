package com.samuelokello.kazihub.presentation.business.job.ui
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.domain.model.job.category.Category
import com.samuelokello.kazihub.domain.model.job.category.FetchCategoryResponse
import com.samuelokello.kazihub.presentation.business.home.components.CategoryDropDown
import com.samuelokello.kazihub.presentation.business.job.event.CreateJobUiEvent
import com.samuelokello.kazihub.presentation.business.job.state.CreateJobUiState
import com.samuelokello.kazihub.presentation.common.ShowLoadingDialog
import com.samuelokello.kazihub.presentation.common.components.CustomButton
import com.samuelokello.kazihub.presentation.common.components.LocationAutocompleteTextField
import com.samuelokello.kazihub.presentation.destinations.HomeScreenDestination
import com.samuelokello.kazihub.presentation.shared.components.EditTextField
import com.samuelokello.kazihub.ui.theme.KaziHubTheme


@Destination
@Composable
fun CreateJobUi(
    viewModel: CreateJobViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val state by viewModel.createJobState.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    Log.e("CreateJobUi", "CreateJobUi: $selectedCategory")
    if(state.isLoading) {
        ShowLoadingDialog()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        KaziHubTheme {
            CreateJobUiContent(
                createJobState = state,
                onCreateJobUiEvent = viewModel::onJobEvent,
                categories = categories,
                selectedCategory = selectedCategory,
                navigateBack = { navigator.popBackStack(HomeScreenDestination.route, inclusive = false) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateJobUiContent(
    createJobState: CreateJobUiState,
    onCreateJobUiEvent: (CreateJobUiEvent) -> Unit,
    categories: FetchCategoryResponse = FetchCategoryResponse(0, emptyList(), "", ""),
    selectedCategory: Category,
    navigateBack: () -> Unit,
) {
    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(bottom = 48.dp),
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Create Job",
                    style = MaterialTheme.typography.titleMedium,
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = navigateBack
                ) { Icon( Icons.Default.ArrowBackIosNew, contentDescription = "Navigate Back" )}
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
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

            EditTextField(
                value = createJobState.budget,
                onValueChange = { onCreateJobUiEvent(CreateJobUiEvent.OnBudgetChange(it)) },
                label = "Budget",
                singleLine = true,
                keyboardOptions =
                KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier.fillMaxWidth(),
            )

            LocationAutocompleteTextField(
                value = createJobState.location,
                onValueChange = { onCreateJobUiEvent(CreateJobUiEvent.OnLocationChange(it)) },
                suggestions = createJobState.locationSuggestion,
                onSuggestionSelected = { onCreateJobUiEvent(CreateJobUiEvent.OnSuggestionSelected(it)) },
                placeholder = "Location",
                modifier = Modifier.fillMaxWidth(),
            )

            EditTextField(
                value = createJobState.qualifications,
                onValueChange = {
                    onCreateJobUiEvent(CreateJobUiEvent.OnQualificationsChange(it.split(","))) },
                label = "Qualifications",
                singleLine = true,
                keyboardOptions =
                KeyboardOptions(
                    imeAction = ImeAction.Done,
                ),
                modifier = Modifier.fillMaxWidth(),
            )

            CategoryDropDown(
                categories = categories.data,
                selectedCategory = selectedCategory,
                onCreateJobUiEvent = onCreateJobUiEvent,
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        Column {
            CustomButton(
                onClick = {
                    onCreateJobUiEvent(
                        CreateJobUiEvent.OnCreateJobClick(
                            title = createJobState.title,
                            description = createJobState.description,
                            budget = createJobState.budget,
                            category = createJobState.categoryId,
                            location = createJobState.location,
                            qualifications = createJobState.qualifications,
                        ),
                    )
                },
                isEnabled =
                createJobState.title.isNotBlank() &&
                        createJobState.description.isNotBlank() &&
                        createJobState.budget.isNotBlank() &&
                        createJobState.location.isNotBlank() &&
                        createJobState.qualifications.isNotBlank(),
                text = "Create Job",
            )
        }


    }
}