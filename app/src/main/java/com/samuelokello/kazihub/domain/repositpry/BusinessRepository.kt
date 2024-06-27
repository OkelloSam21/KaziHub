package com.samuelokello.kazihub.domain.repositpry

import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileRequest
import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileResponse
import com.samuelokello.kazihub.utils.Resource

interface BusinessRepository {
    suspend fun createBusinessProfile(request: BusinessProfileRequest): Resource<BusinessProfileResponse>
    suspend fun fetchBusinessProfileById(id: Int) : Resource<BusinessProfileResponse>
    suspend fun fetchBusinessProfiles(): Resource<BusinessProfileResponse>
    suspend fun fetchBusinessProfileImage()
    suspend fun updateBusinessProfile()
}

