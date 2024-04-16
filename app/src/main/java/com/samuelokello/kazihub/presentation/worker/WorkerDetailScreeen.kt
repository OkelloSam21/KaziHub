package com.samuelokello.kazihub.presentation.worker

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samuelokello.kazihub.ui.theme.KaziHubTheme

@Composable
fun WorkerDetailScreen() {
    val viewModel :WorkerProfileViewModel = viewModel()
    val state by viewModel.profile.collectAsState()

    Surface {
        KaziHubTheme {
            WorkerProfileScreen(viewModel = viewModel, state = state)
        }
    }
}

/**
 * Worker Profile Screen - This is the screen where the worker can view and edit their profile
 * @param viewModel WorkerProfileViewModel
 * @param state WorkerProfileState - the state of the worker profile
 * */
@Composable
fun WorkerProfileScreen(
    viewModel: WorkerProfileViewModel,
    state: WorkerProfileState
    ) {
    Column {
        Text(text = "Create Worker Profile")

        OutlinedTextField(
            value = state.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Worker Name") }
        )
        Button(onClick = { viewModel.updateProfile() }) {
            Text("Update Profile")
        }
    }
}