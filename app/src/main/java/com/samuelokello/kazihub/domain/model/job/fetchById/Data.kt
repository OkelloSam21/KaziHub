package com.samuelokello.kazihub.domain.model.job.fetchById


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("budget")
    val budget: Int,
    @SerializedName("business")
    val business: Business,
    @SerializedName("category")
    val category: Category,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("location")
    val location: String,
    @SerializedName("posted_on")
    val postedOn: String,
    @SerializedName("qualifications")
    val qualifications: List<Qualification>,
    @SerializedName("title")
    val title: String
)