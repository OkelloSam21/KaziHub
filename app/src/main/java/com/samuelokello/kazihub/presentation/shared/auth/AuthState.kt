package com.samuelokello.kazihub.presentation.shared.auth

sealed class AuthState {
    data object Idle : AuthState()
    data object Loading : AuthState()
    data class Success(val data: Any) : AuthState()
    data class Error(val message: String) : AuthState()
}