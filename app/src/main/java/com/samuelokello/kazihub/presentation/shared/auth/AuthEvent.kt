package com.samuelokello.kazihub.presentation.shared.auth

sealed class AuthEvent {
    data class SignUp(val username: String, val email: String, val password: String) : AuthEvent()
    data class SignIn(val email: String, val password: String) : AuthEvent()
    data object GetCurrentUser : AuthEvent()
}