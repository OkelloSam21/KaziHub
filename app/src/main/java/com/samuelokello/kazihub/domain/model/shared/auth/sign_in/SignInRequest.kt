package com.samuelokello.kazihub.domain.model.shared.auth.sign_in

data class SignInRequest(
    val username: String,
    val password: String
)
