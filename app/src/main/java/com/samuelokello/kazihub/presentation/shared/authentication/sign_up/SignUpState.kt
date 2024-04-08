package com.samuelokello.kazihub.presentation.shared.authentication.sign_up

data class SignUpState (
//    val isSignUpSuccessful: Boolean = false,
    val authenticationError: String? = null,
    val signUpSuccess: Boolean = false,
    val userName: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val role: String = "",
    val password: String = "",
    val navigateToHome: Boolean = false,
    val isLoading: Boolean = false,
    val navigateToSignIn: Boolean = false
)