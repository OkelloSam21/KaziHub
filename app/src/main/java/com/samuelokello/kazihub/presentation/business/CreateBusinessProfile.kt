package com.samuelokello.kazihub.presentation.business

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.presentation.business.state.BusinessEvent
import com.samuelokello.kazihub.presentation.business.state.BusinessProfileState
import com.samuelokello.kazihub.presentation.common.HandleError
import com.samuelokello.kazihub.presentation.common.HandleLoading
import com.samuelokello.kazihub.presentation.common.HandleSuccess
import com.samuelokello.kazihub.presentation.common.components.AutocompleteTextField
import com.samuelokello.kazihub.presentation.common.components.CustomButton
import com.samuelokello.kazihub.presentation.destinations.HomeScreenDestination
import com.samuelokello.kazihub.presentation.shared.components.EditTextField
import com.samuelokello.kazihub.ui.theme.KaziHubTheme
import com.samuelokello.kazihub.utils.UserRole

@Composable
fun CreateBusinessProfile(navigator: DestinationsNavigator, userType: UserRole) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        KaziHubTheme {
            val viewModel: BusinessProfileViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()

            BusinessProfileForm(
                state = state,
                onEvent = viewModel::onEvent,
                navigateToDashBoard = { navigator.navigate(HomeScreenDestination(userType)) },
                isFormComplete = viewModel.isFormComplete()
            )
        }
    }

}

@Composable
fun BusinessProfileForm(
    state: BusinessProfileState,
    onEvent: (BusinessEvent) -> Unit,
    navigateToDashBoard: () -> Unit,
    isFormComplete: Boolean
) {

    HandleLoading(state)
    HandleError(state)
    HandleSuccess(state = state, successMessage = state.error)

    LaunchedEffect(state.navigateToHome) {
        if (state.navigateToHome) navigateToDashBoard()
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
                text = "Create Business Profile",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Column {
            EditTextField(
                value = state.email,
                onValueChange = { onEvent(BusinessEvent.OnEmailChanged(it)) },
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
                onValueChange = { onEvent(BusinessEvent.OnPhoneNumberChanged(it)) },
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
            AutocompleteTextField(
                value = state.location,
                onValueChange = { onEvent(BusinessEvent.OnLocationChanged(it)) },
                label = "Location",
                suggestions = state.locationSuggestion,
                onSuggestionSelected = { onEvent(BusinessEvent.OnSuggestionSelected(it)) },
            )
        }
        Column {
            EditTextField(
                value = state.bio,
                onValueChange = { onEvent(BusinessEvent.OnBioChanged(it)) },
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
                        BusinessEvent.OnCreateProfileClicked(
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
