package com.samuelokello.kazihub.data.repository

import com.samuelokello.kazihub.data.model.profile.ProfileResponse
import com.samuelokello.kazihub.data.model.sign_in.SignInRequest
import com.samuelokello.kazihub.data.model.sign_in.SignInResponse
import com.samuelokello.kazihub.data.remote.KaziHubApi
import com.samuelokello.kazihub.domain.model.shared.auth.sign_up.SignUpRequest
import com.samuelokello.kazihub.domain.model.shared.auth.sign_up.SignUpResponse
import com.samuelokello.kazihub.domain.repositpry.AuthRepository
import com.samuelokello.kazihub.utils.Resource
import com.samuelokello.kazihub.utils.TokenManager
import com.samuelokello.kazihub.utils.UserRole
import com.samuelokello.kazihub.utils.safeApiCall
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: KaziHubApi,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun signUp(signUpRequest: SignUpRequest): Resource<SignUpResponse> =
        safeApiCall {
            api.signUp(signUpRequest)
        }

    override suspend fun signIn(signInRequest: SignInRequest): Resource<SignInResponse> =
        safeApiCall {
            api.signIn(signInRequest)
        }.also { signInResponse ->
            tokenManager.setToken(signInResponse.data?.data?.accessToken ?: "", signInResponse.data?.data?.refreshToken ?: "")
        }


    override suspend fun getCurrentUser(): Resource<ProfileResponse> =
        safeApiCall { api.getCurrentUser() }


    override suspend fun refreshToken(): Resource<SignInResponse> =
        safeApiCall { api.refreshToken(tokenManager.getAccessToken()) }

    override suspend fun signOut() {
        tokenManager.clearTokens()
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return tokenManager.hasAccessToken()
    }

    override suspend fun getUserType(): UserRole {
        return when {
            tokenManager.hasAccessToken() -> UserRole.WORKER
            else -> UserRole.BUSINESS
        }
    }

}