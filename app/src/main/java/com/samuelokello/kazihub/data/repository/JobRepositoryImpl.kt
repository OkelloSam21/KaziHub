package com.samuelokello.kazihub.data.repository

import com.google.android.gms.maps.model.LatLng
import com.samuelokello.kazihub.data.remote.KaziHubApi
import com.samuelokello.kazihub.domain.model.job.JobResponse
import com.samuelokello.kazihub.domain.model.job.category.CategoryResponse
import com.samuelokello.kazihub.domain.model.job.category.create.CreateCategoryRequest
import com.samuelokello.kazihub.domain.model.job.category.create.CreateCategoryResponse
import com.samuelokello.kazihub.domain.model.job.create.CreateJobRequest
import com.samuelokello.kazihub.domain.model.job.create.CreateJobsResponse
import com.samuelokello.kazihub.domain.model.job.fetchById.JobDetailsResponse
import com.samuelokello.kazihub.domain.repositpry.JobRepository
import com.samuelokello.kazihub.utils.Resource
import com.samuelokello.kazihub.utils.safeApiCall
import javax.inject.Inject

class JobRepositoryImpl @Inject constructor(
    private val api: KaziHubApi,
) : JobRepository {

    // Jobs
    override suspend fun fetchAllJobs(): Resource<JobResponse> =
        safeApiCall { api.getJobs() }

    override suspend fun fetchRecentJobs(limit: Int): Resource<JobResponse> =
        safeApiCall { api.getRecentJobs(limit) }

    override suspend fun fetchJobById(id: Int): Resource<JobDetailsResponse> =
        safeApiCall { api.getJobById(id) }

    override suspend fun fetchJobCategory(): Resource<CategoryResponse> =
        safeApiCall { api.getJobCategories()}

    override suspend fun fetchJobCategoryById(id: Int): Resource<CategoryResponse> =
        safeApiCall { api.getJobCategoryById(id) }

    override suspend fun fetchJobsByLocation(latLng: LatLng): Resource<JobResponse> =
        safeApiCall { api.getJobsNearby(latLng.longitude, latLng.latitude) }

    override suspend fun fetchJobsByCategory(categoryId: Int): Resource<JobResponse> =
        safeApiCall { api.getJobsByCategory(categoryId) }

    override suspend fun fetchJobByBusiness(businessId: Int): Resource<JobResponse> =
        safeApiCall { api.getJobsByBusiness(businessId) }

    override suspend fun createJob(request: CreateJobRequest): Resource<CreateJobsResponse> =
        safeApiCall { api.createJob(request) }

    override suspend fun createJobCategory(request: CreateCategoryRequest): Resource<CreateCategoryResponse> =
        safeApiCall { api.createJobCategory(request) }

    override suspend fun updateJob(id: Int, request: CreateJobRequest): Resource<CreateJobsResponse> =
        safeApiCall { api.updateJob(id, request) }

    override suspend fun deleteJob(id: Int): Resource<CreateJobsResponse> =
        safeApiCall { api.deleteJob(id) }

    override suspend fun searchJobs(query: String): Resource<JobResponse> =
        safeApiCall { api.searchJobs(query) }

    override suspend fun filterJobsByCategory(categoryId: Int): Resource<JobResponse> =
        safeApiCall { api.getJobsByCategory(categoryId) }

    override suspend fun filterJobsByBusiness(businessId: Int): Resource<JobResponse> =
        safeApiCall { api.getJobsByBusiness(businessId) }

    override suspend fun filterJobsByLocation(latLng: LatLng): Resource<JobResponse> =
        safeApiCall { api.getJobsNearby(latLng.longitude, latLng.latitude) }

    override suspend fun filterRecentJobs(limit: Int): Resource<JobResponse> =
        safeApiCall { api.getRecentJobs(limit) }
}