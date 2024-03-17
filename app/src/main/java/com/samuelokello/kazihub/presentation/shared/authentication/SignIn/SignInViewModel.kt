package com.samuelokello.kazihub.presentation.shared.authentication.SignIn

import androidx.lifecycle.ViewModel
import com.samuelokello.kazihub.presentation.shared.authentication.SignIn.Google.SignInResult
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

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.OnEmailChanged -> {
                _state.update {
                    it.copy(email = event.email)
                }
            }

            is SignInEvent.OnPasswordChanged -> {
                _state.update {
                    it.copy(password = event.password)
                }
            }

            is SignInEvent.OnSignInClicked -> {
                validate()
                _state.update {
                    it.copy(
                        isLoading = true,
                        navigateToHome = it.signInSuccess
                    )
                }
            }
        }
    }

    private fun validate() {
        if (state.value.email.isEmpty() || state.value.password.isEmpty()) {
            _state.update {
                it.copy(
                    signInError = "Please fill in all fields"
                )
            }
            return
        } else {
            _state.update {
                it.copy(
                    signInError = null,
                    signInSuccess = true
                )
            }
        }
    }
}