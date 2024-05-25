package com.samuelokello.kazihub.data.repository

import android.content.Context
import android.util.Log
import com.samuelokello.kazihub.data.remote.KaziHubApi
import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileRequest
import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileResponse
import com.samuelokello.kazihub.domain.repositpry.BusinessRepository
import com.samuelokello.kazihub.utils.Resource
import com.samuelokello.kazihub.utils.getAccessToken
import com.samuelokello.kazihub.utils.handleException
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BusinessRepositoryImpl @Inject constructor(
    private val api: KaziHubApi,
    @ApplicationContext private val context: Context
) : BusinessRepository {
    private val token = getAccessToken(context)
    override suspend fun createBusinessProfile(request: BusinessProfileRequest): Resource<BusinessProfileResponse> {
        return try {
            val response = api.createBusinessProfile(" Bearer $token",request)
            Resource.Success(response)
        } catch (e: Exception) {
            Log.e("KaziHubRepository", "createWorkerProfile: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun fetchBusinessProfileById(id: Int): Resource<BusinessProfileResponse> {
        return try {
            val response = api.getBusinessProfile(id)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun fetchBusinessProfiles(): Resource<BusinessProfileResponse> {
        return try {
            val response = api.getAllBusinessProfiles()
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }
}