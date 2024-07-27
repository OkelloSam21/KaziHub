package com.samuelokello.kazihub.presentation.shared.auth.SignIn

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.kazihub.data.model.sign_in.SignInRequest
import com.samuelokello.kazihub.domain.repositpry.AuthRepository
import com.samuelokello.kazihub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel
@Inject constructor(
    private val repository: AuthRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)

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
            is SignInEvent.OnUserName -> {
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
                                    signInSuccess = true
                                )
                            }
                            val accessToken = result.data?.data?.accessToken
                            result.data?.let {
                                sharedPreferences.edit().putString("accessToken", accessToken)
                                    .apply()
                            }
                            Log.d("SignInViewModel", "onEvent: ${result.data}")
                            hideLoading()
                            checkProfile()
                        }

                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    signInError = result.message
                                )
                            }
                            hideLoading()
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    result.data?.message ?: "",
                                    Toast.LENGTH_LONG
                                ).show()
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
                Log.d("SignInViewModel", "onEvent: Navigate to Sign Up")
            }
        }
    }

    private fun validate() {
        if (state.value.userName.isEmpty()) {
            _state.update { it.copy(signInError = "user name cannot be empty") }
            return
        }

        if (state.value.userName.contains(" ")) {
            _state.update { it.copy(signInError = "userName should not contain a space") }
        }

        if (state.value.password.isEmpty()) {
            _state.update { it.copy(signInError = "password cannot be empty") }
            return
        }

        if (state.value.userName.isNotEmpty() && _state.value.password.isNotEmpty()) {
            _state.update {
                it.copy(
                    signInError = null,
                    signInSuccess = true
                )
            }
        }
    }

    // check if user has already created a profile
    private fun checkProfile() {
        viewModelScope.launch {
            when (val response = repository.getCurrentUser()) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            navigateToProfileCreation = false,
                            navigateToHome = true
                        )
                    }
                    Log.d("SignInViewModel", "checkProfile: ${response.data}")
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            navigateToProfileCreation = true,
                            navigateToHome = false
                        )
                    }
                    Log.d("SignInViewModel", "checkProfile: ${response.message}")
                }
            }
        }
    }
}