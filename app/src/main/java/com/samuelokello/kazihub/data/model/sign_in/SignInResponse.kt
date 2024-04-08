package com.samuelokello.kazihub.data.model.sign_in


import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)