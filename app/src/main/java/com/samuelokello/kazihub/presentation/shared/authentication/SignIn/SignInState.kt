package com.samuelokello.kazihub.presentation.shared.authentication.SignIn

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
    val signInSuccess: Boolean = false,
    val userName: String = "",
    val password: String = "",
    val navigateToHome: Boolean = false,
    val isLoading: Boolean = false,
    val navigateToSignUp: Boolean = false,
)