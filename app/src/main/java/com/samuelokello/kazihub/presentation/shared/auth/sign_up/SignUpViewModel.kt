package com.samuelokello.kazihub.presentation.shared.auth.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.kazihub.domain.model.shared.auth.sign_up.SignUpRequest
import com.samuelokello.kazihub.domain.uscase.SignUpUseCase
import com.samuelokello.kazihub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpUseCase: SignUpUseCase) : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.UserNameChanged -> _state.update { it.copy(userName = event.userName) }
            is SignUpEvent.FirstNameChanged -> _state.update { it.copy(firstName = event.firstName) }
            is SignUpEvent.LastNameChanged -> _state.update { it.copy(lastName = event.lastName) }
            is SignUpEvent.PasswordChanged -> _state.update { it.copy(password = event.password) }
            is SignUpEvent.UserRoleChanged -> _state.update { it.copy(userRole = event.role) }
            is SignUpEvent.SignUpClicked -> signUp()
            SignUpEvent.SignInClicked -> _state.update { it.copy(isSignInClicked = true) }
        }
    }

    private fun signUp() {
        val currentState = _state.value
        if (!validateInputs()) return

        viewModelScope.launch {
            signUpUseCase(
                SignUpRequest(
                    username = currentState.userName,
                    fname = currentState.firstName,
                    lname = currentState.lastName,
                    role = currentState.userRole.name,
                    password = currentState.password
                )
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(isLoading = false, isSignUpSuccessful = true, error = null)
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(isLoading = false, error = result.message)
                        }
                    }
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
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
            currentState.firstName.isBlank() -> {
                _state.update { it.copy(error = "First name cannot be empty") }
                false
            }
            currentState.lastName.isBlank() -> {
                _state.update { it.copy(error = "Last name cannot be empty") }
                false
            }
            currentState.password.length < 8 -> {
                _state.update { it.copy(error = "Password must be at least 8 characters long") }
                false
            }
            else -> true
        }
    }
}
