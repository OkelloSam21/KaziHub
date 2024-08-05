package com.samuelokello.kazihub.domain.model.proposal.proposalResponse


import com.google.gson.annotations.SerializedName

data class ProposalResponseItem(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("job")
    val job: Job,
    @SerializedName("status")
    val status: String,
    @SerializedName("user")
    val user: User
)