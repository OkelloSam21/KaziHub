package com.samuelokello.kazihub.domain.model.job.category


import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)