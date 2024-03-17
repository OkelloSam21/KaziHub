package com.samuelokello.kazihub.presentation.shared.authentication.sign_up

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
//    private val networkService: NetworkService
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnRoleSelected -> {
                _state.update {
                    it.copy(role = event.role)
                }
            }
            is SignUpEvent.OnUserNameChanged -> {
                _state.update {
                    it.copy(userName = event.userName)
                }
            }

            is SignUpEvent.FirstNameChanged -> {
                _state.update {
                    it.copy(firstName = event.firstName)
                }
            }

            is SignUpEvent.LastNameChanged -> {
                _state.update {
                    it.copy(lastName = event.lastName)
                }
            }

            is SignUpEvent.OnPasswordChanged -> {
                _state.update {
                    it.copy(password = event.password)
                }
            }

            is SignUpEvent.OnSignUpClicked -> {
                validate()
                val userName = state.value.userName
                val firstName = state.value.firstName
                val lastName = state.value.lastName
                val role = state.value.role
                val password = state.value.password
                val signUpState = SignUpState(
                    userName = userName,
                    firstName = firstName,
                    lastName = lastName,
                    password = password,
                    role = role
                )
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            navigateToSignIn = true,
                            signUpSuccess = true,
                            signUpError = null
                        )
                    }
//                    val result = networkService.signUpUser(signUpState)
//                    when (result) {
//                        is Resource.
//                        Success -> {
//                            if (result.data is SignUpResponse) {
//                                val signUpResponse = result.data
//                                _state.update { currentState ->
//                                    currentState.copy(
//                                        signUpSuccess = true,
//                                        signUpError = null
//                                    )
//                                }
//                            } else {
//                                // Handle the case where data is not an instance of SignUpResponse
//                            }
//                        }
//
//                        is Resource.Error -> {
//                            _state.update { currentState ->
//                                currentState.copy(
//                                    signUpError = result.message ?: "An error occurred"
//                                )
//                            }
//                        }
//
//                        else -> {}
//                    }

                }
            }

//            is SignUpEvent.OnSignUpClicked -> {
//                validate()
//                val userName = state.value.userName
//                val firstName = state.value.firstName
//                val lastName = state.value.lastName
//                val role = state.value.role
//                val password = state.value.password
//                val signUpState = SignUpState(
//                    userName = userName,
//                    firstName = firstName,
//                    lastName = lastName,
//                    password = password,
//                    role = role
//                )
//                viewModelScope.launch {
//                    val result = networkService.signUpUser(signUpState)
//                    when (result) {
//                        is Resource.Success -> {
//                            val signUpResponse = result.data as SignUpResponse
//                            _state.update { currentState ->
//                                currentState.copy(
//                                    signUpSuccess = true,
//                                    signUpError = null
//                                )
//                            }
//                        }
//
//                        is Resource.Error -> {
//                            _state.update { currentState ->
//                                currentState.copy(
//                                    signUpError = result.message ?: "An error occurred"
//                                )
//                            }
//                        }
//
//                        else -> {}
//                    }
//                }
//            }

            else -> {}
        }
    }

    private fun validate() {

        if (state.value.role.isEmpty() || state.value.userName.isEmpty() || state.value.firstName.isEmpty() || state.value.lastName.isEmpty() || state.value.password.isEmpty()) {
            _state.update {
                it.copy(
                    signUpError = "All fields are required"
                )
            }
        }
        else {
            _state.update {
                it.copy(
                    signUpError = null,
                    signUpSuccess = true
                )
            }
        }
    }
}