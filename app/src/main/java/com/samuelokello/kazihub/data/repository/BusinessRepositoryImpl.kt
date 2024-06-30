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
    private fun getToken() = "Bearer ${getAccessToken(context)}"

    override suspend fun createBusinessProfile(request: BusinessProfileRequest): Resource<BusinessProfileResponse> {
        return try {
            val response = api.createBusinessProfile(getToken(), request)
            Resource.Success(response)
        } catch (e: Exception) {
            Log.e("BusinessRepositoryImpl", "createBusinessProfile: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun fetchBusinessProfileById(id: Int): Resource<BusinessProfileResponse> {
        return try {
            val response = api.getBusinessProfile(id)
            Resource.Success(response)
        } catch (e: Exception) {
            Log.e("BusinessRepositoryImpl", "fetchBusinessProfileById: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun fetchBusinessProfiles(): Resource<List<BusinessProfileResponse>> {
        return try {
            val response = api.getAllBusinessProfiles()
            Resource.Success(response)
        } catch (e: Exception) {
            Log.e("BusinessRepositoryImpl", "fetchBusinessProfiles: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun fetchBusinessProfileImage(profileId: Int): Resource<BusinessProfileResponse> {
        return try {
            val response = api.getBusinessProfileImage(profileId)
            Resource.Success(response)
        } catch (e: Exception) {
            Log.e("BusinessRepositoryImpl", "fetchBusinessProfileImage: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun updateBusinessProfile(id: Int, request: BusinessProfileRequest): Resource<BusinessProfileResponse> {
        return try {
            val response = api.updateBusinessProfile(getToken(), id, request)
            Resource.Success(response)
        } catch (e: Exception) {
            Log.e("BusinessRepositoryImpl", "updateBusinessProfile: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun updateBusinessProfileImage(id: Int, request: BusinessProfileRequest): Resource<BusinessProfileResponse> {
        return try {
            val response = api.updateBusinessProfileImage(getToken(), id, request)
            Resource.Success(response)
        } catch (e: Exception) {
            Log.e("BusinessRepositoryImpl", "updateBusinessProfileImage: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun verifyBusinessByEmail(id: Int, request: BusinessProfileRequest): Resource<BusinessProfileResponse> {
        return try {
            val response = api.verifyBusinessByEmail(id, request)
            Resource.Success(response)
        } catch (e: Exception) {
            Log.e("BusinessRepositoryImpl", "verifyBusinessByEmail: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun verifyBusinessByPhone(id: Int, request: BusinessProfileRequest): Resource<BusinessProfileResponse> {
        return try {
            val response = api.verifyBusinessByPhone(id, request)
            Resource.Success(response)
        } catch (e: Exception) {
            Log.e("BusinessRepositoryImpl", "verifyBusinessByPhone: ${e.message}")
            handleException(e)
        }
    }

    override suspend fun verifyBusinessByCode(id: Int, request: BusinessProfileRequest): Resource<BusinessProfileResponse> {
        return try {
            val response = api.verifyBusinessByCode(id, request)
            Resource.Success(response)
        } catch (e: Exception) {
            Log.e("BusinessRepositoryImpl", "verifyBusinessByCode: ${e.message}")
            handleException(e)
        }
    }
}