package com.samuelokello.kazihub

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.rememberNavHostEngine
import com.samuelokello.kazihub.presentation.shared.NavGraphs
import com.samuelokello.kazihub.presentation.shared.components.StandardScaffold
import com.samuelokello.kazihub.presentation.shared.destinations.HomeScreenDestination
import com.samuelokello.kazihub.presentation.shared.destinations.MessagesScreenDestination
import com.samuelokello.kazihub.presentation.shared.destinations.ProfileScreenDestination
import com.samuelokello.kazihub.presentation.shared.destinations.SettingsScreenDestination
import com.samuelokello.kazihub.ui.theme.KaziHubTheme


class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KaziHubTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    val navHostEngine = rememberNavHostEngine()

                    val newBackStackEntry by navController.currentBackStackEntryAsState()
                    val route = newBackStackEntry?.destination?.route

                    StandardScaffold(
                        navController = navController,
                        showBottomBar = route in listOf(
                            HomeScreenDestination.route,
                            MessagesScreenDestination.route,
                            ProfileScreenDestination.route,
                            SettingsScreenDestination.route
                        )
                    ) {
                        DestinationsNavHost(
                            navGraph = NavGraphs.root,
                            navController = navController,
                            engine = navHostEngine,
                        )
                    }
                }
            }
        }
    }
}
