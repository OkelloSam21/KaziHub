package com.samuelokello.kazihub.presentation.shared.auth.sign_up

import com.samuelokello.kazihub.presentation.common.AuthState

data class SignUpState (
    val signUpError: String? = null,
    val signUpSuccess: Boolean = false,
    val userName: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val navigateToHome: Boolean = false,
    override val isLoading: Boolean = false,
    val navigateCreateProfile: Boolean = false
): AuthState