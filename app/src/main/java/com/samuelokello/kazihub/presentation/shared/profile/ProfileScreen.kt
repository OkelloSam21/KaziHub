package com.samuelokello.kazihub.presentation.shared.profile

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.presentation.worker.ui.profile.WorkerProfileScreen
import com.samuelokello.kazihub.utils.UserRole

@Destination
@Composable
fun ProfileScreen(navigator: DestinationsNavigator, userType: UserRole) {
    when (userType) {
        UserRole.WORKER -> WorkerProfileScreen(navigator)
        UserRole.BUSINESS -> BusinessProfileScreen(navigator)
    }
}



@Composable
fun BusinessProfileScreen(navigator: DestinationsNavigator) {
    Column {
        Text(text = "Business Profile")
    }
}

