package com.samuelokello.kazihub.domain.model.job.category


import com.google.gson.annotations.SerializedName

data class FetchCategoryResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Category>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)