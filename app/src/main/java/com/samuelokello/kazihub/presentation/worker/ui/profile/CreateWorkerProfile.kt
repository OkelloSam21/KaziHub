package com.samuelokello.kazihub.presentation.worker.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.presentation.common.HandleError
import com.samuelokello.kazihub.presentation.common.HandleLoading
import com.samuelokello.kazihub.presentation.common.HandleSuccess
import com.samuelokello.kazihub.presentation.shared.components.CustomButton
import com.samuelokello.kazihub.presentation.shared.components.EditTextField
import com.samuelokello.kazihub.presentation.shared.components.LocationAutocompleteTextField
import com.samuelokello.kazihub.presentation.shared.destinations.HomeScreenDestination
import com.samuelokello.kazihub.presentation.worker.data.CreateWorkerProfileViewModel
import com.samuelokello.kazihub.presentation.worker.state.WorkerEvent
import com.samuelokello.kazihub.presentation.worker.state.WorkerProfileState
import com.samuelokello.kazihub.ui.theme.KaziHubTheme
import com.samuelokello.kazihub.utils.UserRole


@Composable
fun CreateWorkerProfile(
    navigator: DestinationsNavigator,
    userRole: UserRole
) {
    val viewModel: CreateWorkerProfileViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState().value

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        KaziHubTheme {
            WorkerProfileForm(
                state = state,
                onEvent = viewModel::onEvent,
                navigateToHome = { navigator.navigate(HomeScreenDestination(userRole)) },
                isFormComplete = viewModel.isFormComplete()
            )
        }
    }
}

@Composable
fun WorkerProfileForm(
    state: WorkerProfileState,
    onEvent: (WorkerEvent) -> Unit,
    navigateToHome: () -> Unit,
    isFormComplete: Boolean
) {
    HandleLoading(state)
    HandleError(state)
    HandleSuccess(state = state, successMessage = state.error)

    LaunchedEffect(state.navigateToHome) {
        if (state.navigateToHome) navigateToHome()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Column {
            Text(
                text = "Create Worker Profile",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Column {
            EditTextField(
                value = state.email,
                onValueChange = { onEvent(WorkerEvent.OnEmailChanged(it)) },
                label = "Email",
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
            )
        }
        Column {

            EditTextField(
                value = state.phone,
                onValueChange = { onEvent(WorkerEvent.OnPhoneNumberChanged(it)) },
                label = "Phone",
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
            )

        }
        Column {
            LocationAutocompleteTextField(
                value = state.location,
                onValueChange = { onEvent(WorkerEvent.OnLocationChanged(it)) },
                label = "Location",
                suggestions = state.locationSuggestion,
                onSuggestionSelected = { onEvent(WorkerEvent.OnSuggestionSelected(it)) },
            )
        }
        Column {
            EditTextField(
                value = state.bio,
                onValueChange = { onEvent(WorkerEvent.OnBioChanged(it)) },
                label = "Bio",
                singleLine = false,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
            )
        }
        Column {
            CustomButton(
                onClick = {
                    onEvent(
                        WorkerEvent.OnCreateProfileClicked(
                            email = state.email,
                            phone = state.phone,
                            location = state.location,
                            bio = state.bio,
                            selectedLocation = state.selectedLocation
                        )
                    )
                },
                text = "Create Profile",
                isEnabled = isFormComplete
            )
        }
    }
}

