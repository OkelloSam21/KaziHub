package com.samuelokello.kazihub.domain.model.job


import com.google.gson.annotations.SerializedName

data class JobResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val job: List<data>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)