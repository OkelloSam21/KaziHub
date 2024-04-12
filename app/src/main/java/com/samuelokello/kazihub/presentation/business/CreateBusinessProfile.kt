package com.samuelokello.kazihub.presentation.business

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.libraries.places.api.net.PlacesClient
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.presentation.destinations.HomeScreenDestination
import com.samuelokello.kazihub.presentation.shared.components.CustomButton
import com.samuelokello.kazihub.presentation.shared.components.EditTextField
import com.samuelokello.kazihub.presentation.shared.components.LocationDropDown
import com.samuelokello.kazihub.ui.theme.KaziHubTheme
import com.samuelokello.kazihub.utils.UserRole
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun CreateBusinessProfile(navigator: DestinationsNavigator,userType: UserRole) {
    val viewModel: BusinessProfileViewModel = hiltViewModel()
    val state = viewModel.profile.collectAsState().value
    LocalContext.current
    val placesClient = viewModel.getPlacesClient()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        KaziHubTheme {
           BusinessProfileForm(
                state = state,
                viewModel = viewModel,
                placesClient = placesClient,
                navigateToDashBoard = { navigator.navigate(HomeScreenDestination(userType)) }
            )
        }
    }

}

@Composable
fun BusinessProfileForm(
    state: BusinessProfileState,
    viewModel: BusinessProfileViewModel,
    placesClient: PlacesClient,
    navigateToDashBoard : () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        Spacer(modifier = Modifier.weight(.3f))
        Column {
            Text(
                text = "Create Business Profile",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        Column {
            EditTextField(
                value = state.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = "Email",
                error = !viewModel.isEmailValid(state.email),
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
                error = !viewModel.isPhoneValid(state.phone),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            LocationDropDown(
                value = state.location,
                onValueChange = {
                                newLocation, placeId ->
                    viewModel.onLocationChange(newLocation, placeId)
                                },
                places = placesClient,
                label = "Location",
            )
            Spacer(modifier = Modifier.height(16.dp))
            EditTextField(
                value = state.bio,
                onValueChange = { viewModel.onBioChange(it)},
                label = "Bio",
                error = false,
                singleLine = false,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )
        }

        Spacer(modifier = Modifier.weight(2f))
        Column {
            CustomButton(
                onClick = {
                    coroutineScope.launch {
                        viewModel.createProfile()
                    }
//                    navigateToDashBoard()
                },
                text = "Create Profile",
                isEnabled = viewModel.isFormComplete(state.email,state.phone,state.location)
            )
        }
    }
}


