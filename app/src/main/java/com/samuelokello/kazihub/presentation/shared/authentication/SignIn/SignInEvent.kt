package com.samuelokello.kazihub.presentation.shared.authentication.SignIn

sealed interface SignInEvent {
    data class OnUserName(val userName: String): SignInEvent
    data class OnPasswordChanged(val password: String): SignInEvent
    data class OnSignInClicked(val userName:String, val password: String): SignInEvent
    data class OnCreateAccountClicked(val navigateToSignUp: Boolean): SignInEvent
}