package com.samuelokello.kazihub.presentation.worker.ui.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samuelokello.kazihub.presentation.common.components.ProfileImage
import com.samuelokello.kazihub.ui.theme.KaziHubTheme
import com.samuelokello.kazihub.ui.theme.primaryLight

@Composable
fun WorkerProfileScreen() {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        KaziHubTheme {
            WorkerProfileScreenContent()
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WorkerProfileScreenContent() {
    var name by rememberSaveable { mutableStateOf("John Doe") }
    var email by rememberSaveable { mutableStateOf("johndoe@gmail.com") }
    var pasword by rememberSaveable { mutableStateOf("passw0rd") }
    Scaffold(containerColor = MaterialTheme.colorScheme.background) {
        Spacer(modifier = Modifier.height(32.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp)
            ) {
                ProfileImage()
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp),
//            verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Name", modifier = Modifier.width(100.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        unfocusedBorderColor = Color.LightGray,
                        focusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp),
//            verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Email", modifier = Modifier.width(100.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        unfocusedBorderColor = Color.LightGray,
                        focusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp),
//            verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Password", modifier = Modifier.width(100.dp))
                OutlinedTextField(
                    value = pasword,
                    onValueChange = { pasword = it },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        unfocusedBorderColor = Color.LightGray,
                        focusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                OutlinedButton(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryLight,
                        contentColor = Color.White,
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Save Now")
                }
            }
        }
    }
}

@Preview
@Composable
fun WorkerProfileScreenPreview() {
    WorkerProfileScreenContent()
}

@Preview
@Composable
fun WorkerProfileScreenPreviewDark() {
    KaziHubTheme(darkTheme = true) {
        WorkerProfileScreen()
    }
}