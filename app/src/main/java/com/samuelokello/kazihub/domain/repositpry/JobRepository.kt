package com.samuelokello.kazihub.domain.repositpry

import com.google.android.gms.maps.model.LatLng
import com.samuelokello.kazihub.domain.model.job.JobResponse
import com.samuelokello.kazihub.domain.model.job.category.CategoryResponse
import com.samuelokello.kazihub.domain.model.job.category.FetchCategoryResponse
import com.samuelokello.kazihub.domain.model.job.category.create.CreateCategoryRequest
import com.samuelokello.kazihub.domain.model.job.category.create.CreateCategoryResponse
import com.samuelokello.kazihub.domain.model.job.create.CreateJobRequest
import com.samuelokello.kazihub.domain.model.job.create.CreateJobsResponse
import com.samuelokello.kazihub.domain.model.job.fetchById.JobDetailsResponse
import com.samuelokello.kazihub.utils.Resource

interface JobRepository {
    suspend fun fetchAllJobs(): Resource<JobResponse>
    suspend fun fetchRecentJobs(limit: Int): Resource<JobResponse>
    suspend fun fetchJobById(id: Int): Resource<JobDetailsResponse>
    suspend fun fetchJobCategory(): Resource<FetchCategoryResponse>
    suspend fun fetchJobCategoryById(id: Int): Resource<CategoryResponse>
    suspend fun fetchJobsByCategory(categoryId: Int): Resource<JobResponse>
    suspend fun fetchJobsByLocation(latLng: LatLng): Resource<JobResponse>
    suspend fun fetchJobByBusiness(businessId: Int): Resource<JobResponse>
    suspend fun createJob(request: CreateJobRequest): Resource<CreateJobsResponse>
    suspend fun createJobCategory(request: CreateCategoryRequest): Resource<CreateCategoryResponse>
    suspend fun updateJob(id: Int, request: CreateJobRequest): Resource<CreateJobsResponse>
    suspend fun searchJobs(query: String): Resource<JobResponse>
    suspend fun deleteJob(id: Int): Resource<CreateJobsResponse>
    suspend fun filterJobsByCategory(categoryId: Int): Resource<JobResponse>
    suspend fun filterJobsByLocation(latLng: LatLng): Resource<JobResponse>
    suspend fun filterJobsByBusiness(businessId: Int): Resource<JobResponse>
    suspend fun filterRecentJobs(limit: Int): Resource<JobResponse>
}
