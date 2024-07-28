package com.samuelokello.kazihub.presentation.shared.auth.sign_in

sealed class SignInEvent {
    data class UserNameChanged(val userName: String) : SignInEvent()
    data class PasswordChanged(val password: String) : SignInEvent()
    data object SignInClicked : SignInEvent()
    data object CreateAccountClicked : SignInEvent()
}