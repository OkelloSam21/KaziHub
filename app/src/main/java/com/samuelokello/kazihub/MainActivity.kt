package com.samuelokello.kazihub

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresExtension
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.LatLng
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.rememberNavHostEngine
import com.samuelokello.kazihub.presentation.NavGraphs
import com.samuelokello.kazihub.presentation.common.components.StandardScaffold
import com.samuelokello.kazihub.presentation.destinations.HomeScreenDestination
import com.samuelokello.kazihub.presentation.destinations.MessagesScreenDestination
import com.samuelokello.kazihub.presentation.destinations.NotificationsScreenDestination
import com.samuelokello.kazihub.presentation.shared.auth.AuthViewModel
import com.samuelokello.kazihub.ui.theme.KaziHubTheme
import com.samuelokello.kazihub.utils.LocationManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var locationManager: LocationManager

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        locationManager = LocationManager(this)

        requestLocationPermission()

        enableEdgeToEdge()
        setContent {
            KaziHubTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
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
                            NotificationsScreenDestination.route,
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

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Permission was granted, you can now get the user location
                locationManager.getUserLocation(object : LocationManager.LocationCallback {
                    override fun onLocationReceived(location: Location) {
                        // Handle the location

                    }

                    override fun onLocationError(errorMessage: String) {
                        // Handle the error
                    }

                    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
                    override fun onLocationLatLngReceived(latLng: LatLng) {
//                        val latitude = latLng.latitude
//                        val longitude = latLng.longitude

                    }
                })
            } else {
                // Permission was denied, handle the error
            }
        }
}

