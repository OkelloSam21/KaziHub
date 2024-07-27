package com.samuelokello.kazihub.presentation.shared.home.presentation

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.presentation.business.Home.BusinessHomeScreen
import com.samuelokello.kazihub.presentation.worker.ui.home.WorkerHomeScreen
import com.samuelokello.kazihub.utils.UserRole

/***
 * A composable function that displays the home screen of the app.
 * @param userType the type of user that is currently logged in.
 * */
@Destination
@Composable
fun HomeScreen(userType: UserRole = UserRole.BUSINESS, navigator: DestinationsNavigator) {
    when (userType) {
        UserRole.BUSINESS -> BusinessHomeScreen()
        UserRole.WORKER -> WorkerHomeScreen(navigator = navigator)
    }
}