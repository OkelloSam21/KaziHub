package com.samuelokello.kazihub.domain.model.job


import com.google.gson.annotations.SerializedName

data class JobResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("job")
    val job: List<Job>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)