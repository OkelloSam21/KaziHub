package com.samuelokello.kazihub.presentation.shared.profile

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.presentation.business.CreateBusinessProfile
import com.samuelokello.kazihub.presentation.worker.ui.profile.CreateWorkerProfile
import com.samuelokello.kazihub.utils.UserRole

@Suppress("ktlint:standard:function-naming")
@RootNavGraph(start = true)
@Destination
@Composable
fun CreateProfileScreen(
    userType: UserRole = UserRole.WORKER,
    navigator: DestinationsNavigator,
) {
    when (userType) {
        UserRole.BUSINESS -> CreateBusinessProfile(navigator = navigator, userType = userType)
        UserRole.WORKER -> CreateWorkerProfile(navigator, userType)
    }
}
