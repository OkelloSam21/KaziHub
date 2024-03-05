package com.samuelokello.kazihub.presentation.shared.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.samuelokello.kazihub.presentation.shared.navigation.bottom_bar_navigation.BottomNavItem

@Composable
fun StandardScaffold(
    navController: NavHostController,
    showBottomBar: Boolean = true,
    items: List<BottomNavItem> = listOf(
        BottomNavItem.Home,
        BottomNavItem.Messages,
        BottomNavItem.Profile,
        BottomNavItem.Settings
    ),
    content: @Composable (paddingValues: PaddingValues) -> Unit,
) {
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigation(
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.onSurface,
                    elevation = 8.dp,
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    items.forEach { item ->
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = item.title
                                )
                            },
                            label = {
                                Text(
                                    text = item.title,
                                    fontSize = 9.sp
                                )
                            },
                            selected = currentDestination?.route?.contains(item.destination.route)== true,
                            onClick = {
                                navController.navigate(item.destination.route) {
                                    navController.graph.startDestinationId?.let { screenRoute ->
                                        popUpTo(screenRoute) {
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }) { paddingValues ->

        content(paddingValues)
    }
}
