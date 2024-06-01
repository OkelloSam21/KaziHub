package com.samuelokello.kazihub.presentation.business

import android.util.Log
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
import androidx.compose.ui.Modifier
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
import com.samuelokello.kazihub.presentation.common.components.CustomButton
import com.samuelokello.kazihub.presentation.common.components.EditTextField
import com.samuelokello.kazihub.presentation.common.components.LocationDropDown
import com.samuelokello.kazihub.presentation.destinations.HomeScreenDestination
import com.samuelokello.kazihub.ui.theme.KaziHubTheme
import com.samuelokello.kazihub.utils.UserRole

@Composable
fun CreateBusinessProfile(navigator: DestinationsNavigator,userType: UserRole) {


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        KaziHubTheme {
            val viewModel: BusinessProfileViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()

           BusinessProfileForm(
                state = state,
               viewModel = viewModel,
                onEvent = viewModel::onEvent,
                navigateToDashBoard = { navigator.navigate(HomeScreenDestination(userType)) }
           )
        }
    }

}

@Composable
fun BusinessProfileForm(
    state: BusinessProfileState,
    onEvent: (BusinessEvent)-> Unit,
    navigateToDashBoard : () -> Unit,
    viewModel: BusinessProfileViewModel
) {

    HandleLoading(state)
    HandleError(state)
    HandleSuccess(state = state, successMessage = state.error)

    LaunchedEffect (state.navigateToHome){
        if(state.navigateToHome) {
            navigateToDashBoard()
        }
    }

    fun isFormComplete(state: BusinessProfileState): Boolean {
        return state.email.isNotEmpty() && state.phone.isNotEmpty() && state.location.isNotEmpty()
    }
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
                onValueChange = { email ->
                    onEvent(BusinessEvent.OnEmailChanged(email))},
                label = "Email",
//                error = !viewModel.isEmailValid(state.email),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(16.dp))
            EditTextField(
                value = state.phone,
                onValueChange = { phone ->
                                onEvent(BusinessEvent.OnPhoneNumberChanged(phone))
                },
                label = "Phone",
//                error = !viewModel.isPhoneValid(state.phone),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(16.dp))
            LocationDropDown(
                viewModel = viewModel,
                value = state.location ,
                onValueChange = {onEvent(BusinessEvent.OnLocationChanged(it))},
                label = "Location"
            )
            Spacer(modifier = Modifier.height(16.dp))
            EditTextField(
                value = state.bio,
                onValueChange = { bio ->
                    onEvent(BusinessEvent.OnBioChanged(bio))
                },
                label = "Bio",
                error = false,
                singleLine = false,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
            )
        }

        Spacer(modifier = Modifier.weight(2f))
        Column {
            CustomButton(
                onClick = {
                    onEvent(
                        BusinessEvent.OnCreateProfileClicked(
                        email = state.email,
                        phone = state.phone,
                        location = state.location,
                        bio = state.bio
                    ))
                    Log.d("BusinessProfile UI", "createProfile: ${state.navigateToHome}")
                },
                text = "Create Profile",
                isEnabled = state.email.isNotEmpty() && state.phone.isNotEmpty() && state.location.isNotEmpty() && state.bio.isNotEmpty(),

            )
        }

    }
}


