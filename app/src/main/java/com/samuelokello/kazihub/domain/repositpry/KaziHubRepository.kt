package com.samuelokello.kazihub.domain.repositpry

import com.samuelokello.kazihub.data.model.sign_in.SignInRequest
import com.samuelokello.kazihub.data.model.sign_in.SignInResponse
import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileRequest
import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileResponse
import com.samuelokello.kazihub.domain.model.sign_up.SignUpRequest
import com.samuelokello.kazihub.domain.model.sign_up.SignUpResponse
import com.samuelokello.kazihub.domain.model.worker.WorkerProfileRequest
import com.samuelokello.kazihub.domain.model.worker.WorkerProfileResponse
import com.samuelokello.kazihub.utils.Resource

interface KaziHubRepository {
    suspend fun signUp(signUpRequest: SignUpRequest): Resource<SignUpResponse>
    suspend fun signIn(signInRequest: SignInRequest): Resource<SignInResponse>
    suspend fun createBusinessProfile(request: BusinessProfileRequest): Resource<BusinessProfileResponse>
    suspend fun createWorkerProfile(request: WorkerProfileRequest): Resource<WorkerProfileResponse>
//    suspend fun fetchAllJobs(): Resource<List<JobResponse>>
//
//    suspend fun fetchRecentJobs(): Resource<List<JobResponse>>
}
