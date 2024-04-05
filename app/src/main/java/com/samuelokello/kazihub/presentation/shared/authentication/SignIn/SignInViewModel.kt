package com.samuelokello.kazihub.presentation.shared.authentication.SignIn

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.kazihub.data.model.sign_in.SignInRequest
import com.samuelokello.kazihub.data.repository.AuthRepository
import com.samuelokello.kazihub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel
@Inject constructor(private val repository: AuthRepository)
    : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

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

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.OnEmailChanged -> {
                _state.update {
                    it.copy(userName = event.userName)
                }
            }

            is SignInEvent.OnPasswordChanged -> {
                _state.update {
                    it.copy(password = event.password)
                }
            }

            is SignInEvent.OnSignInClicked -> {
                val userName = state.value.userName
                val password = state.value.password
                val signInState = SignInRequest(
                    username = userName,
                    password = password
                )
                viewModelScope.launch(Dispatchers.IO) {
                    showLoading()
                    validate()
                    when (val result = repository.signIn(signInState)) {
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = true,
                                    navigateToHome = it.signInSuccess
                                )
                            }
                            Log.d("SignInViewModel", "onEvent: ${result.data}")
                        }

                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    signInError = result.message
                                )
                            }
                            Log.d("SignInViewModel", "onEvent: ${result.message}")
                        }
                    }

                }

            }

            is SignInEvent.OnCreateAccountClicked -> {
                _state.update {
                    it.copy(
                        navigateToSignUp = true
                    )
                }
            }
        }
    }

    private fun validate() {
        if (state.value.userName.isEmpty() || state.value.password.isEmpty()) {
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