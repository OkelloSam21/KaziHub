package com.samuelokello.kazihub.domain.model.job


import com.google.gson.annotations.SerializedName

data class JobResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: List<Data>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)