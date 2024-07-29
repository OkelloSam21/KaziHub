package com.samuelokello.kazihub.domain.repositpry

import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileRequest
import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileResponse
import com.samuelokello.kazihub.utils.Resource

interface BusinessRepository {
    suspend fun createBusinessProfile(request: BusinessProfileRequest): Resource<BusinessProfileResponse>
    suspend fun fetchBusinessProfileById(id: Int): Resource<BusinessProfileResponse>
    suspend fun fetchBusinessProfiles(): Resource<List<BusinessProfileResponse>>
    suspend fun fetchBusinessProfileImage(profileId: Int): Resource<BusinessProfileResponse>
    suspend fun updateBusinessProfile(id: Int, request: BusinessProfileRequest): Resource<BusinessProfileResponse>
    suspend fun updateBusinessProfileImage(id: Int, request: BusinessProfileRequest): Resource<BusinessProfileResponse>
    suspend fun verifyBusinessByEmail(email: String): Resource<BusinessProfileResponse>
    suspend fun verifyBusinessByPhone(phone: String): Resource<BusinessProfileResponse>
    suspend fun verifyBusinessByCode(code: String): Resource<BusinessProfileResponse>
}
