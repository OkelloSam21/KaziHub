package com.samuelokello.kazihub.presentation.shared.navigation.bottom_bar_navigation


import com.samuelokello.kazihub.R
import com.samuelokello.kazihub.presentation.shared.destinations.Destination
import com.samuelokello.kazihub.presentation.shared.destinations.HomeScreenDestination
import com.samuelokello.kazihub.presentation.shared.destinations.MessagesScreenDestination
import com.samuelokello.kazihub.presentation.shared.destinations.ProfileScreenDestination
import com.samuelokello.kazihub.presentation.shared.destinations.SettingsScreenDestination

sealed class BottomNavItem(var title: String, var icon: Int, var destination: Destination) {
    data object Home : BottomNavItem(
        title = "Home",
        icon = R.drawable.home_1,
        destination = HomeScreenDestination
    )
    data object Messages: BottomNavItem(
        title = "Messages",
        icon = R.drawable.chat_2,
        destination = MessagesScreenDestination
    )
    data object Profile: BottomNavItem(
        title = "Profile",
        icon = R.drawable.male_user,
        destination = ProfileScreenDestination
    )
    data object Settings: BottomNavItem(
        title = "Notifications",
        icon = R.drawable.baseline_notifications_none_24,
        destination = SettingsScreenDestination
    )
}