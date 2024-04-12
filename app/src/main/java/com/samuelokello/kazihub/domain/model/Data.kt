package com.samuelokello.kazihub.domain.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("budget")
    val budget: Double?,
    @SerializedName("business")
    val business: Business?,
    @SerializedName("business_id")
    val businessId: Int?,
    @SerializedName("category")
    val category: Category?,
    @SerializedName("category_id")
    val categoryId: Int?,
    @SerializedName("desc")
    val desc: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("posted_on")
    val postedOn: String?,
    @SerializedName("status")
    val status: Status?,
    @SerializedName("title")
    val title: String?
)