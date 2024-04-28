package com.samuelokello.kazihub.domain.model.job.category.create


import com.google.gson.annotations.SerializedName

data class CreateCategoryResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)