package com.samuelokello.kazihub.presentation.shared.auth.sign_up

import com.samuelokello.kazihub.utils.UserRole

sealed class SignUpEvent {
    data class UserNameChanged(val userName: String) : SignUpEvent()
    data class FirstNameChanged(val firstName: String) : SignUpEvent()
    data class LastNameChanged(val lastName: String) : SignUpEvent()
    data class PasswordChanged(val password: String) : SignUpEvent()
    data class UserRoleChanged(val role: UserRole) : SignUpEvent()
    data object SignUpClicked : SignUpEvent()
    data object SignInClicked: SignUpEvent()
}
