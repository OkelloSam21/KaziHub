package com.samuelokello.kazihub.presentation.shared.authentication.sign_up

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.kazihub.data.model.sign_up.SignUpRequest
import com.samuelokello.kazihub.data.repository.AuthRepository
import com.samuelokello.kazihub.utils.Resource
import com.samuelokello.kazihub.utils.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel
@Inject constructor(
    private val repository: AuthRepository,
    savedStateHandle: SavedStateHandle
    ) : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    private var userRole: UserRole
    init {
        savedStateHandle["userRole"] = UserRole.WORKER.name
        userRole = try {
            UserRole.valueOf(savedStateHandle.get<String>("userRole")?: "")
        } catch (e: Exception) {
            null
        }?: UserRole.WORKER
    }

    private fun showLoading() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
    }

    private fun hideLoading() {
        _state.update {
            it.copy(
                isLoading = false
            )
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnUserRoleChanged -> {
                userRole = event.role
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
                val userName = state.value.userName
                val firstName = state.value.firstName
                val lastName = state.value.lastName
                val password = state.value.password
                val signUpState = SignUpRequest(
                    username = userName,
                    fname = firstName,
                    lname = lastName,
                    role = userRole.name,
                    password = password
                )
                viewModelScope.launch(Dispatchers.IO){
                    showLoading()
                    validate()
                    when(val response = repository.signUp(signUpState)) {
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    navigateToSignIn = true,
                                    signUpSuccess = true,
                                    signUpError = null,
                                )
                            }
                            hideLoading()
                            Log.d("SignUpViewModel", "onEvent: ${response.data}")
                        }

                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    signUpError = response.message ?: "An error occurred"
                                )
                            }
                        }
                    }
                }
            }

//            SignUpEvent.OnSignInClicked -> {
//                _state.update {
//                    it.copy(
//                        navigateToSignIn = true
//                    )
//                }
//            }
        }
    }

    private fun validate() {

        if (state.value.userName.isEmpty() || state.value.firstName.isEmpty() || state.value.lastName.isEmpty() || state.value.password.isEmpty()) {
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