package com.samuelokello.kazihub.domain.model.sign_up

data class SignUpRequest(
    val username: String,
    val fname: String,
    val lname: String,
    val role: String,
    val password: String
)