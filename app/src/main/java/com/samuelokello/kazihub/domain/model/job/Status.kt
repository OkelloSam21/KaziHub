package com.samuelokello.kazihub.domain.model.job


import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("code")
    val code: String?,
    @SerializedName("value")
    val value: String?
)