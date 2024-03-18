package com.samuelokello.kazihub.presentation.shared.authentication.SignIn

sealed interface SignInEvent {
    data class OnEmailChanged(val email: String): SignInEvent
    data class OnPasswordChanged(val password: String): SignInEvent
    data class OnSignInClicked(val email:String, val password: String): SignInEvent
}