package com.samuelokello.kazihub.domain.model.job.fetchById


import com.google.gson.annotations.SerializedName

data class JobDetailsResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)