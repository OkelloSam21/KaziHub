package com.samuelokello.kazihub.domain.model.Bussiness


import com.google.gson.annotations.SerializedName

data class BusinessProfileResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)