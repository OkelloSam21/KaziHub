package com.samuelokello.kazihub.presentation.shared.profile

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.presentation.business.profile.CreateBusinessProfile
import com.samuelokello.kazihub.presentation.worker.ui.profile.CreateWorkerProfile
import com.samuelokello.kazihub.utils.UserRole


//@RootNavGraph(start = true)
@Destination
@Composable
fun CreateProfileScreen(
    userType: UserRole,
    navigator: DestinationsNavigator,
) {
    when (userType) {
        UserRole.BUSINESS -> CreateBusinessProfile(navigator = navigator, userType = userType)
        UserRole.WORKER -> CreateWorkerProfile(navigator, userType)
    }
}
