package com.samuelokello.kazihub.presentation.business

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.samuelokello.kazihub.utils.UserRole


@Composable
fun BusinessProfileScreen(userRole: UserRole) {
    Column {
        Text(text = "Business Profile")
    }
}
