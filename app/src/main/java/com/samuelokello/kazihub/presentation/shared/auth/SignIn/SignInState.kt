package com.samuelokello.kazihub.presentation.shared.auth.SignIn

import com.samuelokello.kazihub.presentation.common.AuthState

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
    val signInSuccess: Boolean = false,
    val userName: String = "",
    val password: String = "",
    val navigateToProfileCreation: Boolean = false,
    override val isLoading: Boolean = false,
    val navigateToSignUp: Boolean = false,
): AuthState