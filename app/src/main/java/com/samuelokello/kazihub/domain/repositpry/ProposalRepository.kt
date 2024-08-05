package com.samuelokello.kazihub.domain.repositpry

import com.samuelokello.kazihub.domain.model.proposal.createProposal.CreateProposalResponse
import com.samuelokello.kazihub.domain.model.proposal.createProposal.ProposalRequest
import com.samuelokello.kazihub.domain.model.proposal.proposalResponse.ProposalResponse
import com.samuelokello.kazihub.utils.Resource

interface ProposalRepository {
    suspend fun createProposal(id: Int, request: ProposalRequest): Resource<CreateProposalResponse>

    suspend fun getProposals(): Resource<ProposalResponse>
}