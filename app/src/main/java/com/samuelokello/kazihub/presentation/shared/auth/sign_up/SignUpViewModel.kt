package com.samuelokello.kazihub.presentation.shared.auth.sign_up

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.kazihub.domain.model.shared.auth.sign_up.SignUpRequest
import com.samuelokello.kazihub.domain.repositpry.AuthRepository
import com.samuelokello.kazihub.utils.Resource
import com.samuelokello.kazihub.utils.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel
@Inject constructor(
    private val repository: AuthRepository,
    savedStateHandle: SavedStateHandle
    ) : ViewModel() {

    var state = mutableStateOf(SignUpState())
        private set

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
        state.value.copy(isLoading = true).also { state.value = it }
    }

    private fun hideLoading() {
        state.value.copy(isLoading = false).also { state.value = it }
    }

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnUserRoleChanged -> {
                userRole = event.role
            }
            is SignUpEvent.OnUserNameChanged -> {
                state.value.copy(userName = event.userName).also { state.value = it }
            }

            is SignUpEvent.FirstNameChanged -> {
                state.value.copy(firstName = event.firstName).also { state.value = it }
            }

            is SignUpEvent.LastNameChanged -> {
                state.value.copy(lastName = event.lastName).also { state.value = it }
            }

            is SignUpEvent.OnPasswordChanged -> {
                state.value.copy(password = event.password).also { state.value = it }
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
                            state.value.copy(
                                navigateCreateProfile = true,
                                signUpSuccess = true,
                                signUpError = null
                            ).also { state.value = it }
                            hideLoading()
                            Log.d("SignUpViewModel", "onEvent: ${response.data}")
                        }

                        is Resource.Error -> {
                            state.value.copy(
                                signUpError = response.message ?: "An error occurred"
                            ).also { state.value = it }
                            hideLoading()
                        }
                    }
                }
            }
        }
    }

    private fun validate() {

        if (state.value.userName.isEmpty() || state.value.firstName.isEmpty() || state.value.lastName.isEmpty() || state.value.password.isEmpty()) {
            state.value.copy(
                signUpError = "All fields are required"
            ).also { state.value = it }
        }
        else {
            state.value.copy(
                signUpError = null
            ).also { state.value = it }
        }
    }
}
