package com.samuelokello.kazihub.presentation.shared.authentication.SignIn

import android.widget.Toast
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
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.samuelokello.kazihub.R

@Destination
@Composable
fun SignInScreen() {
    val viewModel: SignInViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    SignInContent(
        state = state,
        onSignInClick = { viewModel.onSignInClicked() },
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun SignInContent(
    state: SignInState,
    onSignInClick: () -> Unit,
    onEvent: (SignInEvent) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    LaunchedEffect(key1 = state.isSignInSuccessful) {
        if (state.isSignInSuccessful) {
            Toast.makeText(
                context,
                "Sign in successful",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {
        Column {
            Text(
                text = "Welcome back!",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "fill your details or continue with social media",
                style = MaterialTheme.typography.bodySmall
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = state.email,
                onValueChange = { email ->
                    onEvent(SignInEvent.onEmailChanged(email))
                },
                label = { Text(text = "Email") },
//                placeholder = Text(text = "Email"),
                leadingIcon = { Icons.Outlined.Email },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = state.password,
                onValueChange = { password ->
                    onEvent(SignInEvent.onPasswordChanged(password))
                },
                label = { Text(text = "Password") },
//                placeholder = Text(text = "Email"),
                leadingIcon = { Icons.Rounded.Lock },
                trailingIcon = { /*TODO*/ },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            )

            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = "Forgot password?",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    onEvent(SignInEvent.onSignInClicked)
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().height(54.dp)
            ) {
                Text(text = "SIGN IN")
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
                    thickness = 0.5.dp,
                    color = Color.Gray,
                    modifier = Modifier
                        .weight(1f)
                        .width(8.dp)
                )
                Text(
                    text = "Or Connect with",
                    style = MaterialTheme.typography.bodySmall
                )
                HorizontalDivider(
                    thickness = 0.5.dp,
                    color = Color.Gray,
                    modifier = Modifier
                        .weight(1f)
                        .width(8.dp)
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

            Row {
                Text(text = "New User? ")
                TextButton(onClick = { /*TODO*/ }) {
                    Text(
                        text = "Create Account",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}