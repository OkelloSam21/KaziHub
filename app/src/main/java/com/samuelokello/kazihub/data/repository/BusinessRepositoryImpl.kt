package com.samuelokello.kazihub.data.repository

import com.samuelokello.kazihub.data.remote.KaziHubApi
import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileRequest
import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileResponse
import com.samuelokello.kazihub.domain.repositpry.BusinessRepository
import com.samuelokello.kazihub.utils.Resource
import com.samuelokello.kazihub.utils.safeApiCall
import javax.inject.Inject

class BusinessRepositoryImpl @Inject constructor(private val api: KaziHubApi) : BusinessRepository {

    override suspend fun createBusinessProfile(request: BusinessProfileRequest): Resource<BusinessProfileResponse> =
        safeApiCall { api.createBusinessProfile(request) }

    override suspend fun fetchBusinessProfileById(id: Int): Resource<BusinessProfileResponse> =
        safeApiCall { api.getBusinessProfile(id) }

    override suspend fun fetchBusinessProfiles(): Resource<List<BusinessProfileResponse>> =
        safeApiCall { api.getAllBusinessProfiles() }

    override suspend fun fetchBusinessProfileImage(profileId: Int): Resource<BusinessProfileResponse> =
        safeApiCall { api.getBusinessProfileImage(profileId) }

    override suspend fun updateBusinessProfile(id: Int, request: BusinessProfileRequest): Resource<BusinessProfileResponse> =
        safeApiCall { api.updateBusinessProfile(id, request) }

    override suspend fun updateBusinessProfileImage(id: Int, request: BusinessProfileRequest): Resource<BusinessProfileResponse> =
        safeApiCall { api.updateBusinessProfileImage(id, request) }

    override suspend fun verifyBusinessByEmail(email: String): Resource<BusinessProfileResponse> =
        safeApiCall { api.verifyBusinessByEmail(email) }

    override suspend fun verifyBusinessByPhone(phone: String): Resource<BusinessProfileResponse> =
        safeApiCall { api.verifyBusinessByPhone(phone) }

    override suspend fun verifyBusinessByCode(code: String): Resource<BusinessProfileResponse> =
        safeApiCall { api.verifyBusinessByCode(code) }

}