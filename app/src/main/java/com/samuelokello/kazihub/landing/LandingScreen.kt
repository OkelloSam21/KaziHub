package com.samuelokello.kazihub.landing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.presentation.destinations.CreateProfileScreenDestination
import com.samuelokello.kazihub.presentation.destinations.HomeScreenDestination
import com.samuelokello.kazihub.presentation.destinations.OnBoardingScreenDestination
import com.samuelokello.kazihub.presentation.destinations.SignInScreenDestination
import com.samuelokello.kazihub.presentation.destinations.SignUpScreenDestination
import com.samuelokello.kazihub.utils.UserRole

@Composable
fun LandingScreen(
    viewModel: LandingScreenViewModel,
    userType: UserRole,
    navigator: DestinationsNavigator
) {
    val state by viewModel.state.collectAsState()
    LandingScreenContent(state = state,userType = userType, navigator = navigator)
}

@Composable
fun LandingScreenContent(
    state: LandingScreenState,
    userType: UserRole,
    navigator: DestinationsNavigator
) {

    LaunchedEffect(state.destination) {
        when(state.destination) {
            LandingScreenState.Destination.SIGN_IN -> { SignInScreenDestination(userType = userType) }
            LandingScreenState.Destination.ON_BOARDING -> { navigator.navigate(OnBoardingScreenDestination)}
            LandingScreenState.Destination.CREATE_PROFILE -> { navigator.navigate(CreateProfileScreenDestination(userType = userType)) }
            LandingScreenState.Destination.SIGN_UP -> { navigator.navigate(SignUpScreenDestination())}
            LandingScreenState.Destination.USER_TYPE -> { navigator.navigate(SignUpScreenDestination()) }
            LandingScreenState.Destination.HOME -> { navigator.navigate(HomeScreenDestination())}
            null -> {}
        }
    }
}