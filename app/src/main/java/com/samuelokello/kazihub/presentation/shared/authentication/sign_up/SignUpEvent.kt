package com.samuelokello.kazihub.presentation.shared.authentication.sign_up

sealed interface SignUpEvent {
    data class OnUserNameChanged(val userName: String): SignUpEvent
    data class FirstNameChanged(val firstName: String): SignUpEvent
    data class LastNameChanged(val lastName: String): SignUpEvent
    data class OnPasswordChanged(val password: String): SignUpEvent
    data class OnSignUpClicked(
        val userName: String,
        val firstName: String,
        val lastName: String,
        val password: String,
        val role: String
    ): SignUpEvent

    data class OnRoleSelected(val role: String): SignUpEvent
}