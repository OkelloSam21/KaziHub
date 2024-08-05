package com.samuelokello.kazihub.data.remote

import com.samuelokello.kazihub.data.model.profile.ProfileResponse
import com.samuelokello.kazihub.data.model.sign_in.SignInRequest
import com.samuelokello.kazihub.data.model.sign_in.SignInResponse
import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileRequest
import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileResponse
import com.samuelokello.kazihub.domain.model.job.JobResponse
import com.samuelokello.kazihub.domain.model.job.category.CategoryResponse
import com.samuelokello.kazihub.domain.model.job.category.create.CreateCategoryRequest
import com.samuelokello.kazihub.domain.model.job.category.create.CreateCategoryResponse
import com.samuelokello.kazihub.domain.model.job.create.CreateJobRequest
import com.samuelokello.kazihub.domain.model.job.create.CreateJobsResponse
import com.samuelokello.kazihub.domain.model.job.fetchById.JobDetailsResponse
import com.samuelokello.kazihub.domain.model.proposal.createProposal.CreateProposalResponse
import com.samuelokello.kazihub.domain.model.proposal.createProposal.ProposalRequest
import com.samuelokello.kazihub.domain.model.proposal.proposalResponse.ProposalResponse
import com.samuelokello.kazihub.domain.model.shared.auth.sign_up.SignUpRequest
import com.samuelokello.kazihub.domain.model.shared.auth.sign_up.SignUpResponse
import com.samuelokello.kazihub.domain.model.worker.WorkerProfileRequest
import com.samuelokello.kazihub.domain.model.worker.WorkerProfileResponse
import com.samuelokello.kazihub.domain.model.worker.image.WorkerProfileImageRequest
import com.samuelokello.kazihub.domain.model.worker.image.WorkerProfileImageResponse
import com.samuelokello.kazihub.domain.model.worker.skill.WorkerSkillRequest
import com.samuelokello.kazihub.domain.model.worker.skill.WorkerSkillResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface KaziHubApi {
    // Authentication
    @POST("auth/signup")
    suspend fun signUp(@Body request: SignUpRequest): SignUpResponse

    @POST("auth/signin")
    suspend fun signIn(@Body request: SignInRequest): SignInResponse

    @POST("auth/token/refresh")
    suspend fun refreshToken(@Body refreshToken: String): SignInResponse

    @GET("auth/user/profile")
    suspend fun getCurrentUser(): ProfileResponse

    // Business Profile
    @POST("business/profiles/create")
    suspend fun createBusinessProfile(@Body profile: BusinessProfileRequest): BusinessProfileResponse

    @GET("business/profiles/{bus_profile_id}")
    suspend fun getBusinessProfile(@Path("bus_profile_id") id: Int): BusinessProfileResponse

    @GET("business/profiles/all")
    suspend fun getAllBusinessProfiles(): List<BusinessProfileResponse>

    @PUT("business/profiles/{bus_profile_id}/update")
    suspend fun updateBusinessProfile(
        @Path("bus_profile_id") id: Int,
        @Body profile: BusinessProfileRequest
    ): BusinessProfileResponse

    @PUT("business/profiles/{bus_profile_id}/update/image")
    suspend fun updateBusinessProfileImage(
        @Path("bus_profile_id") id: Int,
        @Body profile: BusinessProfileRequest
    ): BusinessProfileResponse

    @GET("business/profiles/image/{profile_id}")
    suspend fun getBusinessProfileImage(@Path("profile_id") id: Int): BusinessProfileResponse

    @POST("business/verify/with_email")
    suspend fun verifyBusinessByEmail(@Body email: String): BusinessProfileResponse

    @POST("business/verify/with_phone")
    suspend fun verifyBusinessByPhone(@Body phone: String): BusinessProfileResponse

    @POST("business/profile/verify")
    suspend fun verifyBusinessByCode(@Body code: String): BusinessProfileResponse

    // Worker Profile
    @POST("worker/profiles/create")
    suspend fun createWorkerProfile(@Body profile: WorkerProfileRequest): WorkerProfileResponse

    @GET("worker/profiles/{worker_profile_id}")
    suspend fun getWorkerProfile(@Path("worker_profile_id") id: Int): WorkerProfileResponse

    @GET("worker/profiles/all")
    suspend fun getAllWorkerProfiles(): List<WorkerProfileResponse>

    @PUT("worker/profiles/{worker_profile_id}/update")
    suspend fun updateWorkerProfile(
        @Path("worker_profile_id") id: Int,
        @Body profile: WorkerProfileRequest
    ): WorkerProfileResponse

    @PUT("worker/profiles/{worker_profile_id}/update/image")
    suspend fun updateWorkerProfileImage(
        @Path("worker_profile_id") id: Int,
        @Body request: WorkerProfileImageRequest
    ): WorkerProfileImageResponse

    @GET("worker/profiles/image/{profile_id}")
    suspend fun getWorkerProfileImage(@Path("profile_id") id: Int): WorkerProfileImageResponse

    // Worker Skills
    @POST("worker/skills/create")
    suspend fun createWorkerSkill(@Body skill: WorkerSkillRequest): WorkerSkillResponse

    @GET("worker/skills/worker/{worker_profile_id}")
    suspend fun getWorkerSkills(@Path("worker_profile_id") id: Int): List<WorkerSkillResponse>

    @PUT("worker/skills/{skill_id}/update")
    suspend fun updateWorkerSkill(
        @Path("skill_id") id: Int,
        @Body skill: WorkerSkillRequest
    ): WorkerSkillResponse

    @DELETE("worker/skills/{skill_id}/delete")
    suspend fun deleteWorkerSkill(@Path("skill_id") id: Int): WorkerSkillResponse

    // Worker Verification
    @POST("worker/verify/with_email")
    suspend fun verifyWorkerByEmail(@Body email: String): WorkerProfileResponse

    @POST("worker/verify/with_phone")
    suspend fun verifyWorkerByPhone(@Body phone: String): WorkerProfileResponse

    @POST("worker/verify/{code}")
    suspend fun verifyWorkerByCode(@Path("code") code: String): WorkerProfileResponse

    // data Categories
    @POST("jobs/category/create")
    suspend fun createJobCategory(@Body category: CreateCategoryRequest): CreateCategoryResponse

    @GET("jobs/category/list")
    suspend fun getJobCategories(): CategoryResponse

    @GET("jobs/category/{category_id}")
    suspend fun getJobCategoryById(@Path("category_id") id: Int): CategoryResponse

    // Jobs
    @POST("jobs/create")
    suspend fun createJob(@Body job: CreateJobRequest): CreateJobsResponse

    @GET("jobs/list")
    suspend fun getJobs(): JobResponse

    @GET("jobs/list/{job_id}")
    suspend fun getJobById(@Path("job_id") id: Int): JobDetailsResponse

    @GET("jobs/list/category/{category_id}")
    suspend fun getJobsByCategory(@Path("category_id") id: Int): JobResponse

    @GET("jobs/list/business/{business_id}")
    suspend fun getJobsByBusiness(@Path("business_id") id: Int): JobResponse

    @PUT("jobs/update/{job_id}")
    suspend fun updateJob(
        @Path("job_id") id: Int,
        @Body job: CreateJobRequest
    ): CreateJobsResponse

    @DELETE("jobs/delete/{job_id}")
    suspend fun deleteJob(@Path("job_id") id: Int): CreateJobsResponse

    @GET("jobs/list/nearby/{lat}/{lon}")
    suspend fun getJobsNearby(
        @Path("lat") lat: Double,
        @Path("lon") lon: Double
    ): JobResponse

    @GET("jobs/list/search/{q}")
    suspend fun searchJobs(@Path("q") query: String): JobResponse

    @GET("jobs/filter")
    suspend fun filterJobs(@Query("q") query: String): List<JobResponse>

    @GET("jobs/recent")
    suspend fun getRecentJobs(@Query("limit") limit: Int): JobResponse

    // Proposals
    @GET("/proposals/")
    suspend fun getProposals(): ProposalResponse

    @POST("/proposals/create/{job_id}")
    suspend fun createProposal(
        @Path("job_id") jobId: Int,
        @Body proposal: ProposalRequest
    ): CreateProposalResponse
}