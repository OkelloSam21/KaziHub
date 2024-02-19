package com.samuelokello.kazihub.presentation.shared.authentication.SignIn

sealed interface SignInEvent {
    data class onEmailChanged(val email: String): SignInEvent
    data class onPasswordChanged(val password: String): SignInEvent
}