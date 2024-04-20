package com.samuelokello.kazihub.presentation.worker.ui.profile

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
import com.samuelokello.kazihub.presentation.shared.components.LocationDropDown
import com.samuelokello.kazihub.presentation.shared.destinations.HomeScreenDestination
import com.samuelokello.kazihub.presentation.worker.data.WorkerProfileViewModel
import com.samuelokello.kazihub.presentation.worker.state.WorkerEvent
import com.samuelokello.kazihub.presentation.worker.state.WorkerProfileState
import com.samuelokello.kazihub.ui.theme.KaziHubTheme
import com.samuelokello.kazihub.utils.UserRole

@Composable
fun CreateWorkerProfile(
    navigator: DestinationsNavigator,
    userRole: UserRole
) {
    val viewModel: WorkerProfileViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState().value
    val placesClient = viewModel.getPlacesClient()
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        KaziHubTheme {
            WorkerProfileForm(
                state = state,
                viewModel = viewModel,
                onEvent = viewModel::onEvent,
                navigateToHome = { navigator.navigate(HomeScreenDestination(userRole)) },
            )
        }
    }
}

@Composable
fun WorkerProfileForm(
    state: WorkerProfileState,
    viewModel: WorkerProfileViewModel,
    onEvent: (WorkerEvent) -> Unit,
    navigateToHome: ()  -> Unit
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
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.weight(.3f))
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
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            EditTextField(
                value = state.phone,
                onValueChange = { onEvent(WorkerEvent.OnPhoneNumberChanged(it))},
                label = "Phone",
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            LocationDropDown(
                viewModel = viewModel,
                value = state.location,
                onValueChange = { onEvent(WorkerEvent.OnLocationChanged(it))},
                label = "Location"
            )

            Spacer(modifier = Modifier.height(16.dp))
            EditTextField(
                value = state.bio,
                onValueChange = { onEvent(WorkerEvent.OnBioChanged(it)) },
                label = "Bio",
                singleLine = false,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )
        }
        Spacer(modifier = Modifier.weight(1.5f))
        Column {
            CustomButton(
                onClick = {
                    onEvent(
                        WorkerEvent.OnCreateProfileClicked(
                            email = state.email,
                            phone = state.phone,
                            location = state.location,
                            bio = state.bio
                        )
                    )
                },
                text = "Create Profile",
                isEnabled = viewModel.isFormComplete(state.email, state.phone, state.location)
            )
        }
    }
}