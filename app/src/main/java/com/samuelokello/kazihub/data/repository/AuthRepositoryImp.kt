package com.samuelokello.kazihub.data.repository

import android.content.Context
import android.util.Log
import com.samuelokello.kazihub.data.model.sign_in.SignInResponse
import com.samuelokello.kazihub.data.remote.KaziHubApi
import com.samuelokello.kazihub.domain.model.shared.auth.sign_up.SignUpRequest
import com.samuelokello.kazihub.domain.model.shared.auth.sign_up.SignUpResponse
import com.samuelokello.kazihub.domain.repositpry.AuthRepository
import com.samuelokello.kazihub.utils.Resource
import com.samuelokello.kazihub.utils.handleException
import com.samuelokello.kazihub.utils.storeAccessToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl
@Inject constructor(
    private val api: KaziHubApi,
    @ApplicationContext private val context: Context
) : AuthRepository{
    override suspend fun signUp(signUpRequest: SignUpRequest):Resource<SignUpResponse> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = api.signUp(signUpRequest)
            Log.d("AuthRepository", "signUp: $response")
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun signIn(signInRequest: com.samuelokello.kazihub.data.model.sign_in.SignInRequest): Resource<SignInResponse> {
        return try {
            val response = api.signIn(signInRequest)
            storeAccessToken(context, response.data?.accessToken!!)
            Log.d("AuthRepository", "signIn: ${response.data.accessToken}")
            Resource.Success(response)

        } catch (e: Exception) {
            handleException(e)
        }
    }

    // check if user profile exist
    override suspend fun checkProfile(): Resource<Boolean> {
        return try {
            val response = api.getAllWorkerProfiles()
            Log.d("AuthRepository", "checkProfile: $response")
            Resource.Success(response.size > 0)
        } catch (e: Exception) {
            handleException(e)
        }
    }


}