package com.samuelokello.kazihub.presentation.shared.auth.SignIn

data class SignInState(
    val userName: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSignInSuccessful: Boolean = false,
    val navigateToSignUp: Boolean = false,
    val navigateToProfileCreation: Boolean = false,
    val navigateToHome: Boolean = false
)