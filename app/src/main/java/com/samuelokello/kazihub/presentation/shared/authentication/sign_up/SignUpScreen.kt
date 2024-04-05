package com.samuelokello.kazihub.presentation.shared.authentication.SignIn

import android.os.Build
import androidx.annotation.RequiresExtension
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
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.R
import com.samuelokello.kazihub.presentation.destinations.SignInScreenDestination
import com.samuelokello.kazihub.presentation.shared.authentication.sign_up.SignUpEvent
import com.samuelokello.kazihub.presentation.shared.authentication.sign_up.SignUpState
import com.samuelokello.kazihub.presentation.shared.authentication.sign_up.SignUpViewModel
import com.samuelokello.kazihub.ui.theme.KaziHubTheme
import com.samuelokello.kazihub.ui.theme.primaryLight
import com.samuelokello.kazihub.utils.UserRole

@Destination
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun SignUpScreen(userRole: UserRole, navigator: DestinationsNavigator) {
    KaziHubTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = androidx.compose.material.MaterialTheme.colors.surface
        ) {
            val viewModel: SignUpViewModel = viewModel()
            val state by viewModel.state.collectAsState()

            SignUpContent(
                state = state,
                onEvent = viewModel::onEvent,
                onClick = {navigator.navigate(SignInScreenDestination)},
                navigateToSIgnIn = {navigator.navigate(SignInScreenDestination)},
                onClearError = viewModel::clearError,
                onSignUpClicked = {}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignUpContent(
    state: SignUpState,
    onEvent: (SignUpEvent) -> Unit,
    onClick: () -> Unit,
    navigateToSIgnIn: () -> Unit,
    onClearError: () -> Unit,
    onSignUpClicked:() -> Unit
) {
    val isPasswordVisible = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val model: SignUpViewModel = viewModel()

    if (state.isLoading) {
        Dialog(
            onDismissRequest = {},
            DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
        ) {
            Surface {
                Card {
                    CircularProgressIndicator(modifier = Modifier.padding(48.dp))
                }
            }
        }
    }

//    if (state.authenticationError != null){
//        val error = state.authenticationError
//        ModalBottomSheet(onDismissRequest = onClearError) {
//            Surface {
//                Column(
//                    modifier =
//                    Modifier
//                        .padding(all = 24.dp)
//                        .padding(bottom = 24.dp),
//                ) {
//                    Text(
//                        text = "Authentication Error",
//                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
//                    )
//                    Text(
//                        modifier = Modifier.padding(vertical = 24.dp),
//                        text = state.authenticationError,
//                    )
//                    Button(
//                        modifier =
//                        Modifier
//                            .fillMaxWidth()
//                            .height(60.dp)
//                            .padding(vertical = 16.dp),
//                        onClick = {},
//                        shape = RoundedCornerShape(10.dp),
//                        colors = ButtonColors(
//                            containerColor = primaryLight,
//                            contentColor = Color.White,
//                            disabledContainerColor = Color.Gray,
//                            disabledContentColor = Color.Black
//                        )
//                    ) {
//                        Text(text = "retry")
//                    }
//                }
//            }
//
//        }
//    }

    LaunchedEffect(state.navigateToSignIn) {
        if ( state.navigateToSignIn) navigateToSIgnIn()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceAround
        ){
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Register Account",
                style = MaterialTheme.typography.titleLarge.copy(fontFamily = FontFamily(Font(R.font.poppins_bold)))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Fill your details or continue with social media",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    color = Color.Gray
                )
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        LaunchedEffect(state.navigateToSignIn) {
            if ( state.navigateToSignIn) navigateToSIgnIn()
        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = state.userName,
                onValueChange = { userName ->
                    onEvent(SignUpEvent.OnUserNameChanged(userName))
                },
                label = { Text(text = "User Name") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.firstName,
                onValueChange = { firstName ->
                    onEvent(SignUpEvent.FirstNameChanged(firstName))
                },
                label = { Text(text = "First Name") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.lastName,
                onValueChange = { lName ->
                    onEvent(SignUpEvent.LastNameChanged(lName))
                },
                label = { Text(text = "Last Name") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.password,
                onValueChange = { password ->
                    onEvent(SignUpEvent.OnPasswordChanged(password))
                },
                label = { Text(text = "Password") },
                placeholder = { Text(text = "Password") },
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
                visualTransformation = if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
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
                onClick = { onSignUpClicked()},
                enabled = model.validate(),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonColors(
                    containerColor = primaryLight,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Black
                )
            ) {
                Text(text = "SIGN UP")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ){
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

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Image(
                        painter = painterResource(id = R.drawable.icons8_google_48),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(50.dp)
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Image(
                        painter = painterResource(id = R.drawable.icons8_facebook_circled_48),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

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