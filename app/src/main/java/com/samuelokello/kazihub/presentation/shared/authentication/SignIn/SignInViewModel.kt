package com.samuelokello.kazihub.presentation.shared.authentication.SignIn

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _state.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }
    }

    fun resetState() {
        _state.update { SignInState() }
    }

    fun onSignInClicked() {

    }

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.onEmailChanged -> {
                _state.update {
                    it.copy(email = event.email)
                }
            }

            is SignInEvent.onPasswordChanged -> {
                _state.update {
                    it.copy(password = event.password)
                }
            }
        }
    }
}