//package com.samuelokello.kazihub.presentation.shared.navigation
//
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.Message
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material.icons.filled.Settings
//import androidx.compose.ui.graphics.vector.ImageVector
//
//data class NavigationedItem(
//    val title: String,
//    val selectedIcon: ImageVector,
//    val unselectedIcon: ImageVector,
//    val badgeCount: Int? = null
//)
//
//sealed class NavigationDrawersItem(
//    val route: String,
//    val icon: ImageVector,
//    val badgeCount: Int? = null
//) {
//    object Home : NavigationDrawerItem(
//        route = "home",
//        icon = Icons.Default.Home
//    )
//
//    object Messages : NavigationDrawerItem(
//        route = "messages",
//        icon = Icons.Default.Message
//    )
//
//    object Profile : NavigationDrawerItem(
//        route = "profile",
//        icon = Icons.Default.Person
//    )
//
//    object Settings : NavigationDrawerItem(
//        route = "settings",
//        icon = Icons.Default.Settings
//    )
//}
//
//val navigationItems = listOf(
//    NavigationItem(
//        title = "Home",
//        selectedIcon = Icons.Default.Home,
//        unselectedIcon = Icons.Default.Home
//    ),
//)
//
//
//
//data class NavigationItem(
//    val title: String,
//    val selectedIcon: ImageVector,
//    val unselectedIcon: ImageVector,
//    val badgeCount: Int? = null
//)
//
//sealed class NavigationDrawerItem(
//    val route: String,
//    val icon: ImageVector,
//    val badgeCount: Int? = null
//) {
//    object Home : NavigationDrawerItem(
//        route = "home",
//        icon = Icons.Default.Home
//    )
//
//    object Messages : NavigationDrawerItem(
//        route = "messages",
//        icon = Icons.Default.Message
//    )
//
//    object Profile : NavigationDrawerItem(
//        route = "profile",
//        icon = Icons.Default.Person
//    )
//
//    object Settings : NavigationDrawerItem(
//        route = "settings",
//        icon = Icons.Default.Settings
//    )
//}
//
//val navigationItem = listOf(
//    NavigationItem(
//        title = "Home",
//        selectedIcon = Icons.Default.Home,
//        unselectedIcon = Icons.Default.Home
//    ),
//)
