package com.samuelokello.kazihub.data.model.profile


import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)