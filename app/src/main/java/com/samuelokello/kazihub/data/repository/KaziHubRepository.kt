package com.samuelokello.kazihub.data.repository

import android.content.Context
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.samuelokello.kazihub.data.model.sign_in.SignInResponse
import com.samuelokello.kazihub.data.remote.KaziHubApi
import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileRequest
import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileResponse
import com.samuelokello.kazihub.domain.model.job.JobResponse
import com.samuelokello.kazihub.domain.model.job.category.CategoryResponse
import com.samuelokello.kazihub.domain.model.job.category.create.CreateCategoryRequest
import com.samuelokello.kazihub.domain.model.job.category.create.CreateCategoryResponse
import com.samuelokello.kazihub.domain.model.job.create.CreateJobRequest
import com.samuelokello.kazihub.domain.model.job.create.CreateJobsResponse
import com.samuelokello.kazihub.domain.model.sign_up.SignUpRequest
import com.samuelokello.kazihub.domain.model.sign_up.SignUpResponse
import com.samuelokello.kazihub.domain.model.worker.WorkerProfileRequest
import com.samuelokello.kazihub.domain.model.worker.WorkerProfileResponse
import com.samuelokello.kazihub.domain.model.worker.image.WorkerProfileImageRequest
import com.samuelokello.kazihub.domain.model.worker.image.WorkerProfileImageResponse
import com.samuelokello.kazihub.domain.model.worker.skill.WorkerSkillRequest
import com.samuelokello.kazihub.domain.repositpry.KaziHubRepository
import com.samuelokello.kazihub.utils.LocationManager
import com.samuelokello.kazihub.utils.Resource
import com.samuelokello.kazihub.utils.getAccessToken
import com.samuelokello.kazihub.utils.storeAccessToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class KaziHubRepositoryImpl
@Inject constructor(
    private val api: KaziHubApi,
    private val locationManager: LocationManager,
    @ApplicationContext private val context: Context
) : KaziHubRepository{
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

    override suspend fun createBusinessProfile(request: BusinessProfileRequest): Resource<BusinessProfileResponse> = withContext(Dispatchers.IO) {
        return@withContext try {
            val token = getAccessToken(context)
            Log.d("KaziHubRepository", "createBusinessProfile: $token")
            val response = api.createBusinessProfile(" Bearer $token",request)
            Resource.Success(response)
        } catch (e: Exception) {
            Log.e("KaziHubRepository", "createWorkerProfile: ${e.message}")
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
    override suspend fun createWorkerProfile(request: WorkerProfileRequest): Resource<WorkerProfileResponse> = withContext(Dispatchers.IO) {
        return@withContext try {
            val token = getAccessToken(context)
            Log.d("KaziHubRepository", "createBusinessProfile: $token")
            val response = api.createWorkerProfile(" Bearer $token",request)
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
            val response = run {
                Log.d("KaziHubRepository", "createBusinessProfile: $token")
                api.updateWorkerProfileImage(" Bearer $token",id,request)
            }
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun updateWorkerProfile(id: Int, request: WorkerProfileRequest): Resource<WorkerProfileResponse> = withContext(Dispatchers.IO) {
        return@withContext try {
            val token = getAccessToken(context)
            val response = run {
                Log.d("KaziHubRepository", "createBusinessProfile: $token")
                api.updateWorkerProfile(" Bearer $token",id,request)
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

    // Jobs
    override suspend fun fetchAllJobs(): Resource<List<JobResponse>> {
        return try {
            val response = api.getJobs()
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun fetchJobById(id: Int): Resource<JobResponse> {
        return try {
            val response = api.getJobById(id = id)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun fetchJobCategories(): Resource<List<CategoryResponse>> {
        return try {
            val response = api.getJobCategories()
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun fetchJobCategoryById(id: Int): Resource<CategoryResponse> {
        return try {
            val response = api.getJobCategoryById(id = id)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun fetchJobsByLocation(latLng: LatLng): Resource<List<JobResponse>> {
        return try {
            val response = api.getJobsNearby(lon = latLng.longitude, lat = latLng.latitude)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun fetchJobsByCategory(categoryID: Int): Resource<List<JobResponse>> {
        return try {
            val response = api.getJobsByCategory(id = categoryID)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun fetchJobsByBusiness(businessId: Int): Resource<List<JobResponse>> {
        return try {
            val response = api.getJobsByBusiness(id = businessId)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun createJob(request: CreateJobRequest): Resource<CreateJobsResponse> {
        return try {
            val token = getAccessToken(context)
            val response = api.createJob(token, request)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun createJobCategory(request: CreateCategoryRequest): Resource<CreateCategoryResponse> {
        return try {
            val response = api.createJobCategory(request)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun updateJob(id: Int, request: CreateJobRequest): Resource<CreateJobsResponse> {
        return try {
            val token = getAccessToken(context)
            val response = api.updateJob(token, id, request)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun deleteJob(id: Int): Resource<CreateJobsResponse> {
        return try {
            val token = getAccessToken(context)
            val response = api.deleteJob(token, id)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun searchJobs(query: String): Resource<List<JobResponse>> {
        return try {
            val response = api.searchJobs(query)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun filterJobsByCategory(categoryId: Int): Resource<List<JobResponse>> {
        return try {
            val response = api.getJobsByCategory(categoryId)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun filterJobsByBusiness(businessId: Int): Resource<List<JobResponse>> {
        return try {
            val response = api.getJobsByBusiness(businessId)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun filterJobsByLocation(latLng: LatLng): Resource<List<JobResponse>> {
        return try {
            val response = api.getJobsNearby(latLng.latitude, latLng.longitude)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun filterRecentJobs(): Resource<List<JobResponse>> {
        return try {
            val response = api.getRecentJobs()
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