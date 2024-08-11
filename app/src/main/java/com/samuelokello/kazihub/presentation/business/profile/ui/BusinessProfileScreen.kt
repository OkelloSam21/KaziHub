package com.samuelokello.kazihub.presentation.business.profile.ui

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

/**
 *  ensure data creation works and data biding works
 *
 */