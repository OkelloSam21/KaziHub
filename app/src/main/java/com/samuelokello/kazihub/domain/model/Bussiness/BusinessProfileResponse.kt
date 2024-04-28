package com.samuelokello.kazihub.domain.model.Bussiness


import com.google.gson.annotations.SerializedName
import com.samuelokello.kazihub.domain.model.common.Data

data class BusinessProfileResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)