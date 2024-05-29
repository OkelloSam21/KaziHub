package com.samuelokello.kazihub.presentation.shared.auth.sign_up

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
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.R
import com.samuelokello.kazihub.presentation.common.HandleError
import com.samuelokello.kazihub.presentation.common.HandleLoading
import com.samuelokello.kazihub.presentation.common.HandleSuccess
import com.samuelokello.kazihub.presentation.destinations.SignInScreenDestination
import com.samuelokello.kazihub.presentation.shared.components.CustomButton
import com.samuelokello.kazihub.presentation.shared.components.EditTextField
import com.samuelokello.kazihub.ui.theme.KaziHubTheme
import com.samuelokello.kazihub.utils.UserRole

@RootNavGraph(start = true)
@Destination
@Composable
fun SignUpScreen(userType: UserRole  = UserRole.WORKER, navigator: DestinationsNavigator) {

    val viewModel: SignUpViewModel = hiltViewModel()
    val state by viewModel.state

    viewModel.onEvent(SignUpEvent.OnUserRoleChanged(userType))

    KaziHubTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface
        ) {
            Log.d("SignUpScreen", "SignUpScreen: ${userType.name}")

            SignUpContent(
                state = state,
                onEvent = viewModel::onEvent,
                navigateToSIgnIn = { navigator.navigate(SignInScreenDestination(userType)) },
                userType = userType
            )
        }
    }
}

@Composable
private fun SignUpContent(
    state: SignUpState,
    onEvent: (SignUpEvent) -> Unit,
    navigateToSIgnIn: () -> Unit,
    userType: UserRole
) {
    val isPasswordVisible = remember { mutableStateOf(false) }
    val isFormValid =
        state.userName.isNotBlank() &&
                state.firstName.isNotBlank() &&
                state.lastName.isNotBlank() &&
                state.password.length > 8

    HandleLoading(state)
    HandleError(state)
    HandleSuccess(state, "Sign Up Successful")
    HandleNavigation(state, navigateToSIgnIn)

    SignUpForm(
        state = state,
        isPasswordVisible = isPasswordVisible,
        isFormValid = isFormValid,
        onEvent = onEvent,
        onClick = navigateToSIgnIn,
        userRole = userType
    )

}

@Composable
fun SignUpForm(
    state: SignUpState,
    isPasswordVisible: MutableState<Boolean>,
    isFormValid: Boolean,
    onEvent: (SignUpEvent) -> Unit,
    onClick: () -> Unit,
    userRole: UserRole
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.register_account),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = R.string.fill_your_details_or_continue_with_social_media),
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            EditTextField(
                label = "User Name",
                value = state.userName,
                onValueChange = {onEvent(SignUpEvent.OnUserNameChanged(it))},
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            EditTextField(
                label = "First Name",
                value = state.firstName,
                onValueChange = {onEvent(SignUpEvent.FirstNameChanged(it))},
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            EditTextField(
                label = "Last Name",
                value = state.lastName,
                onValueChange = {onEvent(SignUpEvent.LastNameChanged(it))},
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.password,
                onValueChange = { password ->
                    onEvent(SignUpEvent.OnPasswordChanged(password))
                },
                label = { Text(text = "Password") },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            isPasswordVisible.value = !isPasswordVisible.value
                        }
                    ) {
                        Icon(
                            imageVector = if (isPasswordVisible.value)
                                Icons.Default.VisibilityOff
                            else Icons.Default.Visibility,
                            contentDescription = null,
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                visualTransformation = if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            TextButton(onClick ={ onClick()}, modifier = Modifier.align(Alignment.End)) {
                Text(
                    text = "Forgot password?",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            CustomButton(
                onClick = {
                    if (isFormValid) {
                        onEvent(
                            SignUpEvent.OnSignUpClicked(
                                state.userName,
                                state.firstName,
                                state.lastName,
                                state.password,
                                role = userRole.name
                            )
                        )
                    }
                },
                isEnabled = isFormValid,
                text = "Sign Up"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

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
                    text = "Or Connect with",
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
        }

        Spacer(modifier = Modifier.height(16.dp))

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

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Already have an Account ? ")
                TextButton(onClick = { onClick() }) {
                    Text(
                        text = "Sign In",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier,
                    )
                }
            }
        }
    }
}

@Composable
fun HandleNavigation(state: SignUpState, navigate: () -> Unit) {
    if (state.navigateCreateProfile) {
        navigate()
    }
}

