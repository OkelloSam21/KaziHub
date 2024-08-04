package com.samuelokello.kazihub.presentation.shared.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.presentation.business.Home.BusinessHomeScreen
import com.samuelokello.kazihub.presentation.destinations.HomeScreenDestination
import com.samuelokello.kazihub.presentation.destinations.UserTYpeScreenDestination
import com.samuelokello.kazihub.presentation.worker.ui.home.WorkerHomeScreen
import com.samuelokello.kazihub.utils.UserRole
import javax.inject.Inject

/***
 * A composable function that displays the home screen of the app.
 * @param userType the type of user that is currently logged in.
 * */
@Destination
@Composable
fun HomeScreen(
    userType: UserRole,
    navigator: DestinationsNavigator,
    authViewModel: AuthViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = true) {
        if (!authViewModel.isUserLoggedIn()) {
            navigator.navigate(UserTYpeScreenDestination) {
                popUpTo(HomeScreenDestination.route) { inclusive = true }
            }
        }
    }

    when (userType) {
        UserRole.BUSINESS -> BusinessHomeScreen()
        UserRole.WORKER -> WorkerHomeScreen(navigator = navigator)
    }
}

class AuthViewModel @Inject constructor(): ViewModel() {
    fun isUserLoggedIn(): Boolean {
        return true
    }


}