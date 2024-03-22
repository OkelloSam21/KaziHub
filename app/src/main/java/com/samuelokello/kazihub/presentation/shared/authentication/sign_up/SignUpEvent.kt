package com.samuelokello.kazihub.presentation.shared.authentication.sign_up

import com.samuelokello.kazihub.utils.UserRole

sealed interface SignUpEvent {
    data class OnUserNameChanged(val userName: String): SignUpEvent
    data class FirstNameChanged(val firstName: String): SignUpEvent
    data class LastNameChanged(val lastName: String): SignUpEvent
    data class OnPasswordChanged(val password: String): SignUpEvent
    data class OnUserRoleChanged(val role: UserRole): SignUpEvent
//    data object OnSignInClicked: SignUpEvent
    data class OnSignUpClicked(
        val userName: String,
        val firstName: String,
        val lastName: String,
        val password: String,
        val role: String
    ): SignUpEvent

}