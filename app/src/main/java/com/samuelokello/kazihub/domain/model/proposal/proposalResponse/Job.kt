package com.samuelokello.kazihub.domain.model.proposal.proposalResponse


import com.google.gson.annotations.SerializedName

data class Job(
    @SerializedName("budget")
    val budget: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user")
    val user: Int
)