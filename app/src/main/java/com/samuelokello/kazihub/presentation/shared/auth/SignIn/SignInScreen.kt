package com.samuelokello.kazihub.presentation.shared.auth.SignIn

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.R
import com.samuelokello.kazihub.presentation.common.HandleError
import com.samuelokello.kazihub.presentation.common.HandleLoading
import com.samuelokello.kazihub.presentation.common.HandleSuccess
import com.samuelokello.kazihub.presentation.destinations.CreateProfileScreenDestination
import com.samuelokello.kazihub.presentation.shared.components.CustomButton
import com.samuelokello.kazihub.presentation.shared.components.EditTextField
import com.samuelokello.kazihub.ui.theme.KaziHubTheme
import com.samuelokello.kazihub.utils.UserRole

//@RootNavGraph(start = true)
@Destination
@Composable
fun SignInScreen(navigator: DestinationsNavigator, userType: UserRole = UserRole.WORKER) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        KaziHubTheme {
            val viewModel: SignInViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()

            SignInContent(
                state = state,
                onEvent = viewModel::onEvent,
                navigateToProfileCreation = {
                    navigator.navigate(
                        CreateProfileScreenDestination(
                            userType
                        )
                    )
                },
                navigateToSignUp = { navigator.popBackStack() }
            )
        }
    }
}

@Composable
private fun SignInContent(
    state: SignInState,
    onEvent: (SignInEvent) -> Unit,
    navigateToProfileCreation: () -> Unit,
    navigateToSignUp: () -> Unit
) {
    val isPasswordVisible = remember { mutableStateOf(false) }
    val isFormComplete  = state.userName.isNotEmpty() && state.password.isNotEmpty()

    HandleLoading(state)
    HandleError(state)
    HandleSuccess(state = state, successMessage = state.error)

    LaunchedEffect(state.navigateToProfileCreation) {
        if (state.navigateToProfileCreation) {
            navigateToProfileCreation()
        }
    }
    LaunchedEffect(state.navigateToSignUp) {
        if (state.navigateToProfileCreation) {
            navigateToSignUp()
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {
        SignInHeader()
        SignInForm(state, isPasswordVisible, onEvent, isFormComplete)
        SignInFooter(navigateToSignUp)
    }
}

@Composable
fun SignInHeader() {
    Column {
        Spacer(modifier = Modifier.height(64.dp))

        Text(
            text = stringResource(R.string.welcome_back),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.fill_your_details_or_continue_with_social_media),
            style = MaterialTheme.typography.titleLarge
        )
    }
}

/**
 * A composable function that displays the sign in form
 * @param state: SignInState - the state of the sign in screen
 * @param isPasswordVisible: MutableState<Boolean> - the state of the password visibility
 * @param onEvent: (SignInEvent) -> Unit - the event handler
 * @param isFormValid boolean variable for checking if form is filled
 *
 */
@Composable
fun SignInForm(
    state: SignInState,
    isPasswordVisible: MutableState<Boolean>,
    onEvent: (SignInEvent) -> Unit,
    isFormValid: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = state.userName,
            onValueChange = { email ->
                onEvent(SignInEvent.OnUserName(email))
            },
            label = { Text(text = "User Name") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        EditTextField(
            label = "Password",
            value = state.password,
            onValueChange = { password -> onEvent(SignInEvent.OnPasswordChanged(password)) },
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = {
                    isPasswordVisible.value = !isPasswordVisible.value
                }) {
                    Icon(
                        imageVector = if (isPasswordVisible.value) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = null
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        TextButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = "Forgot password?",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        CustomButton(
            onClick = {
                onEvent(
                    SignInEvent.OnSignInClicked(
                        state.userName,
                        state.password
                    )
                )
            },
            isEnabled = isFormValid,
            text = "SIGN IN"
        )

    }
}

/**
 * A composable function that displays the sign in footer
 * @param navigateToSignUp: () -> Unit - the event handler for navigating to the sign up screen
 */
@Composable
fun SignInFooter(navigateToSignUp: () -> Unit) {
    Spacer(modifier = Modifier.height(32.dp))
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .width(4.dp)
                    .padding(top = 8.dp),
                thickness = 0.5.dp,
                color = Color.Gray
            )
            Text(
                text = stringResource(R.string.or_connect_with),
                style = MaterialTheme.typography.bodyLarge
            )
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .width(4.dp)
                    .padding(top = 8.dp),
                thickness = 0.5.dp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Image(
                        painter = painterResource(id = R.drawable.icons8_google_48),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(64.dp)
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Image(
                        painter = painterResource(id = R.drawable.icons8_facebook_circled_48),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "New User ? ")
                TextButton(onClick = { navigateToSignUp() }) {
                    Text(
                        text = "Create Account",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier,
                    )
                }
            }
        }
    }
}

/**
 * A composable function that handles navigation to the home screen
 * @param state: SignInState - the state of the sign in screen
 * @param navigate: () -> Unit - the event handler for navigating to the home screen
 */
@Composable
fun HandleNavigation(state: SignInState, navigate: () -> Unit) {
    try {
        when {
            state.navigateToProfileCreation -> {
                Log.d("SignInScreen", "HandleNavigation: Navigate to Home")
                navigate()
            }

            state.navigateToSignUp -> {
                Log.d("SignInScreen", "HandleNavigation: Navigate to Sign Up")
                navigate()
            }
        }
    } catch (e: Exception) {
        Log.e("SignInScreen", "HandleNavigation: exception")
    }

}