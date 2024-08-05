package com.samuelokello.kazihub.domain.model.proposal.createProposal

data class ProposalRequest(
    val amount: Int,
    val cv: String,
    val userId: Int,
    val jobId: Int
)
