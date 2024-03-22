package com.samuelokello.kazihub.presentation.shared.authentication.SignIn

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.R
import com.samuelokello.kazihub.presentation.shared.destinations.HomeScreenDestination
import com.samuelokello.kazihub.ui.theme.KaziHubTheme
import com.samuelokello.kazihub.ui.theme.primaryLight

@Destination
@Composable
fun SignInScreen(navigator: DestinationsNavigator) {
    KaziHubTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val viewModel: SignInViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()

            SignInContent(
                state = state,
                onEvent = viewModel::onEvent,
                navigateToHome = { navigator.navigate(HomeScreenDestination) },
                navigateToSignUp = { navigator.popBackStack() }
            )
        }
    }
}

@Composable
private fun SignInContent(
    state: SignInState,
    onEvent: (SignInEvent) -> Unit,
    navigateToHome: () -> Unit,
    navigateToSignUp: () -> Unit
) {
    val context = LocalContext.current
    val isPasswordVisible = remember { mutableStateOf(false) }
//    val isPasswordValid by derivedStateOf { (state.password ?: "").length > 8 }

    if (state.isLoading) {
        Dialog(
            onDismissRequest = {},
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Surface {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = primaryLight
                    )
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(48.dp)
                            .background(
                                color = MaterialTheme.colorScheme.background,
                                shape = RoundedCornerShape(8.dp)
                            )
                    )
                }
            }
        }
        Toast.makeText(context, "Sign in successful", Toast.LENGTH_LONG).show()
    }

    LaunchedEffect(key1 = state.signInError) { state.signInError?.let { error -> Toast.makeText(context, error, Toast.LENGTH_LONG).show() } }

    LaunchedEffect(state.navigateToHome) { if (state.navigateToHome) navigateToHome() }

    LaunchedEffect(state.navigateToSignUp) { if (state.navigateToSignUp)navigateToSignUp() }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {
        Column {
            Spacer(modifier = Modifier.height(64.dp))

            Text(
                text = stringResource(R.string.welcome_back),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = FontFamily(
                        Font(R.font.poppins_bold)
                    )
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.fill_your_details_or_continue_with_social_media),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = FontFamily(
                        Font(R.font.poppins_medium)
                    ),
                    color = Color.Gray
                )
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = state.userName,
                onValueChange = { userName ->
                    onEvent(SignInEvent.OnEmailChanged(userName))
                },
                label = { Text(text = "User Name") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = state.password,
                onValueChange = { password ->
                    onEvent(SignInEvent.OnPasswordChanged(password))
                },
                label = { Text(text = "Password") },
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
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            )

            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = "Forgot password?",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    onEvent(
                        SignInEvent.OnSignInClicked(
                            state.userName,
                            state.password
                        )
                    )
                },
                shape = RoundedCornerShape(10.dp),
//                enabled = isPasswordValid && state.userName.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryLight,
                    contentColor = Color.White,
                    disabledContentColor = Color.Gray,
                    disabledContainerColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {
                Text(text = "SIGN IN")
            }
        }

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
                TextButton(onClick = { onEvent(SignInEvent.OnCreateAccountClicked) }) {
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