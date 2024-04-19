package com.samuelokello.kazihub.presentation.shared.authentication.sign_up

import com.samuelokello.kazihub.presentation.common.AuthState

data class SignUpState (
//    val isSignUpSuccessful: Boolean = false,
    val signUpError: String? = null,
    val signUpSuccess: Boolean = false,
    val userName: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val role: String = "",
    val password: String = "",
    val navigateToHome: Boolean = false,
    override val isLoading: Boolean = false,
    val navigateToSignIn: Boolean = false
): AuthState