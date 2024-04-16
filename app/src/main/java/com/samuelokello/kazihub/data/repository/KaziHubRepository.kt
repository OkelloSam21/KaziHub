package com.samuelokello.kazihub.data.repository

import android.content.Context import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.samuelokello.kazihub.data.model.sign_in.SignInResponse
import com.samuelokello.kazihub.data.remote.KaziHubApi
import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileRequest
import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileResponse
import com.samuelokello.kazihub.domain.model.sign_up.SignUpRequest
import com.samuelokello.kazihub.domain.model.sign_up.SignUpResponse
import com.samuelokello.kazihub.utils.LocationManager
import com.samuelokello.kazihub.utils.Resource
import com.samuelokello.kazihub.utils.getAccessToken
import com.samuelokello.kazihub.utils.storeAccessToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class KaziHubRepository
@Inject constructor(
    private val api: KaziHubApi,
    private val locationManager: LocationManager,
    @ApplicationContext private val context: Context
) {
    suspend fun signUp(signUpRequest: SignUpRequest):Resource<SignUpResponse> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = api.signUp(signUpRequest)
            Log.d("AuthRepository", "signUp: $response")
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }


    suspend fun signIn(signInRequest: com.samuelokello.kazihub.domain.model.shared.auth.sign_in.SignInRequest): Resource<SignInResponse> = withContext(Dispatchers.IO){
        return@withContext try {
            val response = api.sigIn(signInRequest)
            storeAccessToken(context, response.data?.accessToken!!)
            Log.d("AuthRepository", "signIn: ${response.data.accessToken}")
            Resource.Success(response)

        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun createBusinessProfile(request: BusinessProfileRequest): Resource<BusinessProfileResponse> = withContext(Dispatchers.IO) {
        return@withContext try {
            val token = getAccessToken(context)
            val response =if (token != null) {
                api.createBusinessProfile(" Bearer $token",request)
            } else {
                throw Exception("Token is null")
            }
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun fetchBusinessProfile(id: Int): Resource<BusinessProfileResponse>  = withContext(Dispatchers.IO){
        return@withContext try {
            val response = api.getBusinessProfile(id)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    private fun <T>handleException(e: Exception): Resource<T>{
        return if (e is HttpException && (e.hashCode() == 401 || e.hashCode() == 403)) {
            Resource.Error("Authorization failed. Please check your credentials.")
        } else {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}