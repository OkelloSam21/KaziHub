package com.samuelokello.kazihub.presentation.shared.auth.sign_in

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.kazihub.domain.uscase.GetCurrentUserUseCase
import com.samuelokello.kazihub.domain.uscase.SignInUseCase
import com.samuelokello.kazihub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.UserNameChanged -> _state.update { it.copy(userName = event.userName) }
            is SignInEvent.PasswordChanged -> _state.update { it.copy(password = event.password) }
            is SignInEvent.SignInClicked -> signIn()
            is SignInEvent.CreateAccountClicked -> _state.update { it.copy(navigateToSignUp = true) }
        }
    }

    private fun signIn() {
        val currentState = _state.value
        if (!validateInputs()) return

        viewModelScope.launch {
            Log.d("SignInViewModel", "initiate signIn: ${currentState.userName}")
            signInUseCase(currentState.userName, currentState.password).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.d("SignInViewModel", "signIn successful")
                        _state.update { it.copy(isLoading = false, isSignInSuccessful = true) }
                        checkProfile()
                    }
                    is Resource.Error -> {
                        Log.d("SignInViewModel", "signIn error: ${result.message}")
                        _state.update { it.copy(isLoading = false, error = result.message) }
                    }
                    is Resource.Loading -> {
                        Log.d("SignInViewModel", "signIn loading")
                        _state.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    private fun checkProfile() {
        viewModelScope.launch {
            getCurrentUserUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.d("SignInViewModel", "checkProfile successful")
                        _state.update { it.copy(navigateToHome = true) }
                    }
                    is Resource.Error -> {
                        Log.d("SignInViewModel", "checkProfile error: ${result.message}")
                        _state.update { it.copy(navigateToProfileCreation = true) }
                    }
                    is Resource.Loading -> {
                        // Do nothing, we're already signed in
                    }
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        val currentState = _state.value
        return when {
            currentState.userName.isBlank() -> {
                _state.update { it.copy(error = "Username cannot be empty") }
                false
            }
            currentState.userName.contains(" ") -> {
                _state.update { it.copy(error = "Username should not contain spaces") }
                false
            }
            currentState.password.isBlank() -> {
                _state.update { it.copy(error = "Password cannot be empty") }
                false
            }
            else -> true
        }
    }

}