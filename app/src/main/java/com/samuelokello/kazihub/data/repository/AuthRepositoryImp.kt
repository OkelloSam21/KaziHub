package com.samuelokello.kazihub.data.repository

import android.util.Log
import com.samuelokello.kazihub.data.model.profile.ProfileResponse
import com.samuelokello.kazihub.data.model.sign_in.SignInRequest
import com.samuelokello.kazihub.data.model.sign_in.SignInResponse
import com.samuelokello.kazihub.data.remote.KaziHubApi
import com.samuelokello.kazihub.di.TokenManager
import com.samuelokello.kazihub.domain.model.shared.auth.sign_up.SignUpRequest
import com.samuelokello.kazihub.domain.model.shared.auth.sign_up.SignUpResponse
import com.samuelokello.kazihub.domain.repositpry.AuthRepository
import com.samuelokello.kazihub.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: KaziHubApi,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun signUp(signUpRequest: SignUpRequest): Resource<SignUpResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.signUp(signUpRequest)
                Log.d(TAG, "signUp: $response")
                Resource.Success(response)
            } catch (e: Exception) {
                Log.e(TAG, "signUp error: ${e.message}", e)
                Resource.Error(e.message ?: "An unexpected error occurred")
            }
        }

    override suspend fun signIn(signInRequest: SignInRequest): Resource<SignInResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.signIn(signInRequest)
                response.data?.accessToken?.let { tokenManager.setToken(it) }
                Log.d(TAG, "signIn: ${response.data?.accessToken}")
                Resource.Success(response)
            } catch (e: Exception) {
                Log.e(TAG, "signIn error: ${e.message}", e)
                Resource.Error(e.message ?: "An unexpected error occurred")
            }
        }

    override suspend fun getCurrentUser(): Resource<ProfileResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.getCurrentUser()
                Log.d(TAG, "getCurrentUser: $response")
                Resource.Success(response)
            } catch (e: Exception) {
                Log.e(TAG, "getCurrentUser error: ${e.message}", e)
                Resource.Error(e.message ?: "An unexpected error occurred")
            }
        }

    override suspend fun refreshToken(): Resource<SignInResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.refreshToken(tokenManager.getToken())
                response.data?.accessToken?.let { tokenManager.setToken(it) }
                Log.d(TAG, "refreshToken: ${response.data?.accessToken}")
                Resource.Success(response)
            } catch (e: Exception) {
                Log.e(TAG, "refreshToken error: ${e.message}", e)
                Resource.Error(e.message ?: "An unexpected error occurred")
            }
        }

    companion object {
        private const val TAG = "AuthRepositoryImpl"
    }
}