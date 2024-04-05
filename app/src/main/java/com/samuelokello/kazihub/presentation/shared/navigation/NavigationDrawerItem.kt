package com.samuelokello.kazihub.presentation.shared.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationDrawerItem(
    val route: String,
    val icon: ImageVector,
    val badgeCount: Int? = null
) {
    object Home : NavigationDrawerItem(
        route = "home",
        icon = Icons.Default.Home
    )

    object Messages : NavigationDrawerItem(
        route = "messages",
        icon = Icons.AutoMirrored.Filled.Message
    )

    object Profile : NavigationDrawerItem(
        route = "profile",
        icon = Icons.Default.Person
    )

    object Settings : NavigationDrawerItem(
        route = "settings",
        icon = Icons.Default.Settings
    )
}