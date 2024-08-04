package com.samuelokello.kazihub.presentation.shared.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.kazihub.domain.repositpry.AuthRepository
import com.samuelokello.kazihub.utils.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    private val authState: StateFlow<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        viewModelScope.launch {
            _authState.value = if (authRepository.isUserLoggedIn()) {
                AuthState.Authenticated(authRepository.getUserType())
            } else {
                AuthState.Unauthenticated
            }
        }
    }

    fun isUserLoggedIn(): Boolean {
        return authState.value is AuthState.Authenticated
    }

    fun getUserType(): UserRole {
        return when (val state = authState.value) {
            is AuthState.Authenticated -> state.userType
            else -> throw IllegalStateException("User is not authenticated")
        }
    }


    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
            _authState.value = AuthState.Unauthenticated
        }
    }
}
