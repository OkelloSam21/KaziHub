package com.samuelokello.kazihub.presentation.shared.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.samuelokello.kazihub.presentation.business.BusinessProfileViewModel

@Composable
fun BusinessProfileScreen(viewModel: BusinessProfileViewModel) {
    val state by viewModel.profile.collectAsState()

    Column {
        Text(text = "Profile")

    }
}
