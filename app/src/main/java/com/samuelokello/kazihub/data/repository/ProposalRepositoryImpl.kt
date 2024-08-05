package com.samuelokello.kazihub.data.repository

import com.samuelokello.kazihub.data.remote.KaziHubApi
import com.samuelokello.kazihub.domain.model.proposal.createProposal.CreateProposalResponse
import com.samuelokello.kazihub.domain.model.proposal.createProposal.ProposalRequest
import com.samuelokello.kazihub.domain.model.proposal.proposalResponse.ProposalResponse
import com.samuelokello.kazihub.domain.repositpry.ProposalRepository
import com.samuelokello.kazihub.utils.Resource
import com.samuelokello.kazihub.utils.safeApiCall
import javax.inject.Inject

class ProposalRepositoryImpl @Inject constructor(private val api: KaziHubApi): ProposalRepository {
    override suspend fun createProposal(id: Int, request: ProposalRequest): Resource<CreateProposalResponse> {
        return safeApiCall {
            api.createProposal(id, request)
        }
    }

    override suspend fun getProposals(): Resource<ProposalResponse> {
        return safeApiCall {
            api.getProposals()
        }
    }
}