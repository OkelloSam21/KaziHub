package com.samuelokello.kazihub.domain.model.Bussiness

data class BusinessProfileRequest(
    val email: String,
    val phone: String,
    val bio: String,
    val location: String,
    val latitude: Number,
    val longitude: Number
)