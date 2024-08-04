package com.samuelokello.kazihub.presentation.shared.auth

import com.samuelokello.kazihub.utils.UserRole

sealed class AuthState {
    data object Idle : AuthState()
    data object Loading : AuthState()
    data class Success(val data: Any) : AuthState()
    data object Initial : AuthState()
    data class Authenticated(val userType: UserRole) : AuthState()
    data object Unauthenticated : AuthState()
    data class Error(val message: String) : AuthState()
}