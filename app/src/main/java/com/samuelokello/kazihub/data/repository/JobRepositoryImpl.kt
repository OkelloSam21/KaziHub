package com.samuelokello.kazihub.data.repository

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import com.samuelokello.kazihub.data.remote.KaziHubApi
import com.samuelokello.kazihub.domain.model.job.JobResponse
import com.samuelokello.kazihub.domain.model.job.category.CategoryResponse
import com.samuelokello.kazihub.domain.model.job.category.create.CreateCategoryRequest
import com.samuelokello.kazihub.domain.model.job.category.create.CreateCategoryResponse
import com.samuelokello.kazihub.domain.model.job.create.CreateJobRequest
import com.samuelokello.kazihub.domain.model.job.create.CreateJobsResponse
import com.samuelokello.kazihub.domain.repositpry.JobRepository
import com.samuelokello.kazihub.utils.Resource
import com.samuelokello.kazihub.utils.getAccessToken
import com.samuelokello.kazihub.utils.handleException
import javax.inject.Inject

class JobRepositoryImpl @Inject constructor(
    private val api: KaziHubApi,
    private val context: Context
) : JobRepository {

    // Jobs
    override suspend fun fetchAllJobs(): Resource<JobResponse> {
        return try {
            val response = api.getJobs()
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun fetchRecentJobs(limit: Int): Resource<JobResponse> {
        return try {
            val response = api.getRecentJobs(limit)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun fetchJobById(id: Int): Resource<JobResponse> {
        return try {
            val response = api.getJobById(id = id)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun fetchJobCategory(): Resource<CategoryResponse> {
        return try {
            val response = api.getJobCategories()
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun fetchJobCategoryById(id: Int): Resource<CategoryResponse> {
        return try {
            val response = api.getJobCategoryById(id = id)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun fetchJobsByLocation(latLng: LatLng): Resource<JobResponse>{
        return try {
            val response = api.getJobsNearby(lon = latLng.longitude, lat = latLng.latitude)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun fetchJobsByCategory(categoryId: Int): Resource<JobResponse> {
        return try {
            val response = api.getJobsByCategory(id = categoryId)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun fetchJobByBusiness(businessId: Int): Resource<JobResponse>{
        return try {
            val response = api.getJobsByBusiness(id = businessId)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun createJob(request: CreateJobRequest): Resource<CreateJobsResponse> {
        return try {
            val token = getAccessToken(context)
            val response = api.createJob(token, request)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun createJobCategory(request: CreateCategoryRequest): Resource<CreateCategoryResponse> {
        return try {
            val response = api.createJobCategory(request)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun updateJob(id: Int, request: CreateJobRequest): Resource<CreateJobsResponse> {
        return try {
            val token = getAccessToken(context)
            val response = api.updateJob(token, id, request)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun deleteJob(id: Int): Resource<CreateJobsResponse> {
        return try {
            val token = getAccessToken(context)
            val response = api.deleteJob(token, id)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun searchJobs(query: String): Resource<JobResponse> {
        return try {
            val response = api.searchJobs(query)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun filterJobsByCategory(categoryId: Int): Resource<JobResponse> {
        return try {
            val response = api.getJobsByCategory(categoryId)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun filterJobsByBusiness(businessId: Int): Resource<JobResponse> {
        return try {
            val response = api.getJobsByBusiness(businessId)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun filterJobsByLocation(latLng: LatLng): Resource<JobResponse>{
        return try {
            val response = api.getJobsNearby(latLng.latitude, latLng.longitude)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun filterRecentJobs(limit: Int): Resource<JobResponse> {
        return try {
            val response = api.getRecentJobs(limit)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

}