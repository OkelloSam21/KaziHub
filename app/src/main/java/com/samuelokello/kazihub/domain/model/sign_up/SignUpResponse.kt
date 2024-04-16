package com.samuelokello.kazihub.domain.model.sign_up


import com.google.gson.annotations.SerializedName
import com.samuelokello.kazihub.data.model.sign_up.Data

data class SignUpResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)