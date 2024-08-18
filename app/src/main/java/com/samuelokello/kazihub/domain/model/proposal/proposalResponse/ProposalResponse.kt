package com.samuelokello.kazihub.domain.model.proposal.proposalResponse


import com.google.gson.annotations.SerializedName

data class ProposalResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: ProposalData
)

