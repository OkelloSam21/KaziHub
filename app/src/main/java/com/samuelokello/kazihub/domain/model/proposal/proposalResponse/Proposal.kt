package com.samuelokello.kazihub.domain.model.proposal.proposalResponse


import com.google.gson.annotations.SerializedName

data class ProposalData(
    @SerializedName("message")
    val message: String?,
    @SerializedName("cv")
    val cv: String,
    @SerializedName("status")
    val status: Status,
    @SerializedName("job_id")
    val jobId: Int,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("id")
    val id: Int,
    @SerializedName("user_id")
    val userId: Int
)