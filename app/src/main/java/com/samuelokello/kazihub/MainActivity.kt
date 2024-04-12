package com.samuelokello.kazihub

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.LatLng
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.rememberNavHostEngine
import com.samuelokello.kazihub.presentation.NavGraphs
import com.samuelokello.kazihub.presentation.destinations.HomeScreenDestination
import com.samuelokello.kazihub.presentation.destinations.MessagesScreenDestination
import com.samuelokello.kazihub.presentation.destinations.ProfileScreenDestination
import com.samuelokello.kazihub.presentation.destinations.SettingsScreenDestination
import com.samuelokello.kazihub.presentation.shared.components.StandardScaffold
import com.samuelokello.kazihub.ui.theme.KaziHubTheme
import com.samuelokello.kazihub.utils.LocationManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationManager = LocationManager(this)

        requestLocationPermission()

        setContent {
            KaziHubTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
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

                    override fun onLocationLatLngReceived(latLng: LatLng) {
                        val latitude = latLng.latitude
                        val longitude = latLng.longitude
                    }
                })
            } else {
                // Permission was denied, handle the error
            }
        }
}


//@AndroidEntryPoint
//class MainActivity : ComponentActivity() {
//
//    private val LOCATION_PERMISSION_REQUEST_CODE = 1
//    private lateinit var locationManager: LocationManager
//
//    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContent {
//            KaziHubTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    val navController = rememberNavController()
//                    val navHostEngine = rememberNavHostEngine()
//
//                    val newBackStackEntry by navController.currentBackStackEntryAsState()
//                    val route = newBackStackEntry?.destination?.route
//
//                    StandardScaffold(
//                        navController = navController,
//                        showBottomBar = route in listOf(
//                            HomeScreenDestination.route,
//                            MessagesScreenDestination.route,
//                            ProfileScreenDestination.route,
//                            SettingsScreenDestination.route
//                        )
//                    ) {
//                        DestinationsNavHost(
//                            navGraph = NavGraphs.root,
//                            navController = navController,
//                            engine = navHostEngine,
//                        )
//                    }
//                }
//            }
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        val requestPermissionLauncher = registerForActivityResult( ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
//            permissions.entries.forEach {
//                val permissionName = it.key
//                val isGranted = it.value
//            }
//
//        }
//        when (requestCode) {
//            LOCATION_PERMISSION_REQUEST_CODE -> {
//                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // Permission was granted, you can now get the user location
//                    locationManager.getUserLocation(object : LocationManager.LocationCallback {
//                        override fun onLocationReceived(location: Location) {
//                            // Handle the location
//                        }
//
//                        override fun onLocationError(errorMessage: String) {
//                            // Handle the error
//                        }
//                    })
//                } else {
//                    // Permission was denied, handle the error
//                }
//                return
//            }
//        }
//    }
//
//    sealed class PermissionRequestResult {
//        object Granted : PermissionRequestResult()
//        data class Denied(val deniedPermissions: List<String>) : PermissionRequestResult()
//        object Error : PermissionRequestResult()
//    }
//
//    val requestPermissionLauncher = registerForActivityResult(
//        ActivityResultContracts.RequestMultiplePermissions()
//    ) { result ->
//        val permissionRequestResult = when {
//            result.all { it.value } -> PermissionRequestResult.Granted
//            result.any { !it.value } -> PermissionRequestResult.Denied(result.filter { !it.value }.keys.toList())
//            else -> PermissionRequestResult.Error
//        }
//
//        // Handle the permission request result
//
//        when (permissionRequestResult) {
//            PermissionRequestResult.Granted -> {
//                // Permission was granted, get the user location
//                locationManager.getUserLocation { location ->
//                    // Handle the location
//                }
//            }
//
//            PermissionRequestResult.Denied -> {
//                // Permission was denied, handle the error
//            }
//
//            PermissionRequestResult.Error -> {
//                // An error occurred, handle the error
//            }
//        }
//    }
//
//
//}
