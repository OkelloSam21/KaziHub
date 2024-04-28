package com.samuelokello.kazihub.presentation.business

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun BusinessProfileScreen(viewModel: BusinessProfileViewModel) {
    val state by viewModel.state.collectAsState()

    Column {
        Text(text = "Profile")

    }
}