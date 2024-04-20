package com.samuelokello.kazihub.data.repository

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.samuelokello.kazihub.data.model.sign_in.SignInResponse
import com.samuelokello.kazihub.data.remote.KaziHubApi
import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileRequest
import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileResponse
import com.samuelokello.kazihub.domain.model.shared.auth.sign_in.SignInRequest
import com.samuelokello.kazihub.domain.model.sign_up.SignUpRequest
import com.samuelokello.kazihub.domain.model.sign_up.SignUpResponse
import com.samuelokello.kazihub.domain.model.worker.WorkerProfileRequest
import com.samuelokello.kazihub.domain.model.worker.WorkerProfileResponse
import com.samuelokello.kazihub.domain.model.worker.image.WorkerProfileImageRequest
import com.samuelokello.kazihub.domain.model.worker.image.WorkerProfileImageResponse
import com.samuelokello.kazihub.domain.model.worker.skill.WorkerSkillRequest
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


    suspend fun signIn(signInRequest: SignInRequest): Resource<SignInResponse> = withContext(Dispatchers.IO){
        return@withContext try {
            val response = api.signIn(signInRequest)
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
                Log.d("KaziHubRepository", "createBusinessProfile: $token")
                api.createBusinessProfile(" Bearer $token",request)
            } else {
                Log.d("KaziHubRepository", "createBusinessProfile: Token is null")
                throw Exception("Token is null")
            }
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun fetchBusinessProfileById(id: Int): Resource<BusinessProfileResponse>  = withContext(Dispatchers.IO){
        return@withContext try {
            val response = api.getBusinessProfile(id)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun fetchAllBusinessProfiles(): Resource<List<BusinessProfileResponse>> = withContext(Dispatchers.IO){
        return@withContext try {
            val response = api.getAllBusinessProfiles()
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    // worker
    suspend fun createWorkerProfile(request: WorkerProfileRequest): Resource<WorkerProfileResponse> = withContext(Dispatchers.IO) {
        return@withContext try {
            val token = getAccessToken(context)
            val response =if (token != null) {
                Log.d("KaziHubRepository", "createBusinessProfile: $token")
                api.createWorkerProfile(" Bearer $token",request)
            } else {
                Log.d("KaziHubRepository", "createBusinessProfile: Token is null")
                throw Exception("Token is null")
            }
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun fetchWorkerProfileById(id: Int): Resource<WorkerProfileResponse>  = withContext(Dispatchers.IO){
        return@withContext try {
            val response = api.getWorkerProfile(id)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun fetchAllWorkerProfiles(): Resource<List<WorkerProfileResponse>> = withContext(Dispatchers.IO){
        return@withContext try {
            val response = api.getAllWorkerProfiles()
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    // not complete need to work on the request
    suspend fun updateWorkerProfileImage(id: Int, request: WorkerProfileImageRequest): Resource<WorkerProfileImageResponse> = withContext(Dispatchers.IO) {
        return@withContext try {
            val token = getAccessToken(context)
            val response =if (token != null) {
                Log.d("KaziHubRepository", "createBusinessProfile: $token")
                api.updateWorkerProfileImage(" Bearer $token",id,request)
            } else {
                Log.d("KaziHubRepository", "createBusinessProfile: Token is null")
                throw Exception("Token is null")
            }
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun updateWorkerProfile(id: Int, request: WorkerProfileRequest): Resource<WorkerProfileResponse> = withContext(Dispatchers.IO) {
        return@withContext try {
            val token = getAccessToken(context)
            val response =if (token != null) {
                Log.d("KaziHubRepository", "createBusinessProfile: $token")
                api.updateWorkerProfile(" Bearer $token",id,request)
            } else {
                Log.d("KaziHubRepository", "createBusinessProfile: Token is null")
                throw Exception("Token is null")
            }
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun fetchWorkerImage(id: Int) = withContext(Dispatchers.IO){
        return@withContext try {
            val response = api.getWorkerProfileImage(id)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun createSkills(bearer: String, request: WorkerSkillRequest) = withContext(Dispatchers.IO){
        return@withContext try {
            val response = api.createWorkerSkill(bearer,request)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun deleteSkill(bearer: String, id: Int) = withContext(Dispatchers.IO){
        return@withContext try {
            val response = api.deleteWorkerSkill(bearer,id)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun verifyWorkerWithEmail(bearer: String) = withContext(Dispatchers.IO){
        return@withContext try {
            val response = api.verifyWorkerByEmail(bearer)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun verifyWorkerWithPhone(bearer: String) = withContext(Dispatchers.IO){
        return@withContext try {
            val response = api.verifyWorkerByPhone(bearer)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun verifyWorkerWithCode(bearer: String) = withContext(Dispatchers.IO){
        return@withContext try {
            val response = api.verifyWorkerByCode(bearer)
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