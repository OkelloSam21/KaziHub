package com.samuelokello.kazihub.presentation.shared.home.presentation

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.samuelokello.kazihub.presentation.business.BusinessHomeScreen
import com.samuelokello.kazihub.presentation.worker.WorkerHomeScreen
import com.samuelokello.kazihub.utils.UserRole

/***
 * A composable function that displays the home screen of the app.
 * @param userType the type of user that is currently logged in.
 * */
@Destination
@Composable
fun HomeScreen(userType: UserRole) {
    when (userType) {
        UserRole.BUSINESS -> BusinessHomeScreen()
        UserRole.WORKER -> WorkerHomeScreen()
    }
}