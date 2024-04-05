package com.samuelokello.kazihub.presentation.shared.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.presentation.business.CreateBusinessProfile
import com.samuelokello.kazihub.presentation.worker.WorkerProfile
import com.samuelokello.kazihub.utils.UserRole

@RequiresApi(Build.VERSION_CODES.M)
@Destination
@Composable
fun CreateProfileScreen(userType: UserRole, navigator: DestinationsNavigator) {
    when(userType) {
        UserRole.BUSINESS -> CreateBusinessProfile(navigator,userType)
        UserRole.WORKER -> WorkerProfile(navigator,userType)
    }
}