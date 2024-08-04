package com.samuelokello.kazihub.presentation.shared.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.presentation.destinations.HomeScreenDestination
import com.samuelokello.kazihub.presentation.destinations.OnBoardingScreenDestination
import com.samuelokello.kazihub.presentation.shared.auth.AuthViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween<Float>(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        if (viewModel.isUserLoggedIn()) {
            val userType = viewModel.getUserType()
            navigator.navigate(HomeScreenDestination(userType))
        } else {
            navigator.navigate(OnBoardingScreenDestination)
        }
    }
}