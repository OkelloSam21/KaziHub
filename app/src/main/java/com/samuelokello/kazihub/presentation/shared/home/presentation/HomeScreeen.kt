package com.samuelokello.kazihub.presentation.shared.home.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.presentation.business.Home.BusinessHomeScreen
import com.samuelokello.kazihub.presentation.shared.auth.AuthViewModel
import com.samuelokello.kazihub.presentation.worker.ui.home.WorkerHomeScreen
import com.samuelokello.kazihub.utils.UserRole

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
    when (userType) {
        UserRole.BUSINESS -> BusinessHomeScreen()
        UserRole.WORKER -> WorkerHomeScreen(navigator = navigator)
    }
}
