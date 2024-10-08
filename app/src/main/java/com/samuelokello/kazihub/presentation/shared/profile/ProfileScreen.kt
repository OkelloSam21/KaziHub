package com.samuelokello.kazihub.presentation.shared.profile

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.presentation.business.profile.ui.BusinessProfileScreen
import com.samuelokello.kazihub.presentation.worker.ui.profile.WorkerProfileScreen
import com.samuelokello.kazihub.utils.UserRole

@Destination
@Composable
fun ProfileScreen(navigator: DestinationsNavigator, userType: UserRole) {
    when (userType) {
        UserRole.WORKER -> WorkerProfileScreen(userRole = userType)
        UserRole.BUSINESS -> BusinessProfileScreen(userRole = userType)
    }
}
