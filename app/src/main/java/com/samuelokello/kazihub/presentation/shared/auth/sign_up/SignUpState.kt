package com.samuelokello.kazihub.presentation.shared.auth.sign_up

import com.samuelokello.kazihub.utils.UserRole

data class SignUpState(
    val userName: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val userRole: UserRole = UserRole.WORKER,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSignUpSuccessful: Boolean = false
)