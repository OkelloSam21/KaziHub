package com.samuelokello.kazihub.domain.model.proposal.createProposal


import com.google.gson.annotations.SerializedName

data class CreateProposalResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)