package com.samuelokello.kazihub.domain.model.worker

data class WorkerProfileRequest(
    val email: String,
    val phone: String,
    val bio: String,
    val location: String,
    val latitude: Number,
    val longitude: Number
)
