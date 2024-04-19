package com.samuelokello.kazihub.presentation.worker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.libraries.places.api.net.PlacesClient
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.presentation.shared.components.CustomButton
import com.samuelokello.kazihub.presentation.shared.components.EditTextField
import com.samuelokello.kazihub.utils.UserRole

@Composable
fun WorkerProfile(
    navigator: DestinationsNavigator,
    userRole: UserRole
) {
    val viewModel: WorkerProfileViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState().value
    val placesClient = viewModel.getPlacesClient()

    WorkerProfileForm(
        state = state,
        viewModel = viewModel,
        placesClient = placesClient,
        navigateToHome = {  }
    )
}

@Composable
fun WorkerProfileForm(
    state: WorkerProfileState,
    viewModel: WorkerProfileViewModel,
    placesClient: PlacesClient,
    navigateToHome: ()  -> Unit
) {
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
                onValueChange = { viewModel.onEmailChange(it) },
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
                onValueChange = { viewModel.onPhoneChange(it) },
                label = "Phone",
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
//            LocationDropDown(
//                value = state.location,
//                onValueChange = { newValue, input ->
//                    viewModel.onLocationChange(newValue)
//                    viewModel.onLocationInputChanged(input)
//                }
//                locationSuggestions = ,
//                onLocationInputChanged = ,
//                label =
//            )

            Spacer(modifier = Modifier.height(16.dp))
            EditTextField(
                value = state.bio,
                onValueChange = { viewModel.onBioChange(it) },
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
                onClick = { navigateToHome() },
                text = "Create Profile",
                isEnabled = viewModel.isFormComplete(state.email, state.phone, state.location)
            )
        }
    }
}