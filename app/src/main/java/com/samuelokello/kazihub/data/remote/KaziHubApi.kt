package com.samuelokello.kazihub.data.remote

import com.samuelokello.kazihub.data.model.sign_in.SignInResponse
import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileRequest
import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileResponse
import com.samuelokello.kazihub.domain.model.JobsResponse
import com.samuelokello.kazihub.domain.model.sign_up.SignUpRequest
import com.samuelokello.kazihub.domain.model.sign_up.SignUpResponse
import com.samuelokello.kazihub.domain.model.worker.WorkerProfileRequest
import com.samuelokello.kazihub.domain.model.worker.WorkerProfileResponse
import com.samuelokello.kazihub.domain.model.worker.skill.WorkerSkillRequest
import com.samuelokello.kazihub.domain.model.worker.skill.WorkerSkillResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface KaziHubApi {
    @POST("auth/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): SignUpResponse

    @POST("auth/signin")
    suspend fun signIn(@Body signInRequest: com.samuelokello.kazihub.domain.model.shared.auth.sign_in.SignInRequest): SignInResponse

    @POST("/business/profiles/create")
    suspend fun createBusinessProfile(
        @Header("Authorization") token: String,
        @Body businessProfile: BusinessProfileRequest
    ): BusinessProfileResponse

    @GET("/business/profiles/{bus_profile_id}")
    suspend fun getBusinessProfile(
        @Path("bus_profile_id") id: Int
    ): BusinessProfileResponse

    @GET("/business/profiles/all/")
    suspend fun getAllBusinessProfiles(): List<BusinessProfileResponse>

    @PUT("/business/profiles/{bus_profile_id}/update")
    suspend fun updateBusinessProfile(
        @Header("Authorization") token: String,
        @Path("bus_profile_id") id: Int,
        @Body businessProfile: BusinessProfileRequest
    ): BusinessProfileResponse

    @PUT("/business/profiles/{bus_profile_id}/update/image")
    suspend fun updateBusinessProfileImage(
        @Header("Authorization") token: String,
        @Path("bus_profile_id") id: Int,
        @Body businessProfile: BusinessProfileRequest
    ): BusinessProfileResponse

    @PUT("/business/profiles/image/{profile_id}")
    suspend fun getBusinessProfileImage(
        @Path("profile_id") id: Int
    ): BusinessProfileResponse

    @GET("/business/verify/with_email/")
    suspend fun verifyBusinessByEmail(
        @Path("bus_profile_id") id: Int,
        @Body businessProfile: BusinessProfileRequest
    ): BusinessProfileResponse

    @GET("/business/verify/with_phone/")
    suspend fun verifyBusinessByPhone(
        @Path("bus_profile_id") id: Int,
        @Body businessProfile: BusinessProfileRequest
    ): BusinessProfileResponse

    @GET("/business/profile/verify/")
    suspend fun verifyBusinessByCode(
        @Path("bus_profile_id") id: Int,
        @Body businessProfile: BusinessProfileRequest
    ): BusinessProfileResponse


    @GET("jobs/list")
    suspend fun getJobs(): List<JobsResponse>

    // worker
    @POST("/worker/profiles/create")
    suspend fun createWorkerProfile(
        @Header("Authorization") token: String,
        @Body workerProfile: WorkerProfileRequest
    ): WorkerProfileResponse

    @GET("/worker/profiles/{worker_profile_id}")
    suspend fun getWorkerProfile(
        @Path("worker_profile_id") id: Int
    ): WorkerProfileResponse

    @GET("/worker/profiles/all/")
    suspend fun getAllWorkerProfiles(): List<WorkerProfileResponse>

    @PUT("/worker/profiles/{worker_profile_id}/update")
    suspend fun updateWorkerProfile(
        @Header("Authorization") token: String,
        @Path("worker_profile_id") id: Int,
        @Body workerProfile: WorkerProfileRequest
    ): WorkerProfileResponse

    @PUT("/worker/profiles/{worker_profile_id}/update/image")
    suspend fun updateWorkerProfileImage(
        @Header("Authorization") token: String,
        @Path("worker_profile_id") id: Int,
        @Body workerProfile: WorkerProfileRequest
    ): WorkerProfileResponse

    @GET("/worker/profiles/image/{profile_id}")
    suspend fun getWorkerProfileImage(
        @Path("profile_id") id: Int
    ): WorkerProfileResponse

    @POST("/worker/skills/create")
    suspend fun createWorkerSkill(
        @Header("Authorization") token: String,
        @Body workerSkill: WorkerSkillRequest
    ): WorkerSkillResponse

    @GET("/worker/skills/worker/{worker_profile_id}")
    suspend fun getWorkerSkills(
        @Path("worker_profile_id") id: Int
    ): List<WorkerSkillResponse>

    @PUT("/worker/skills/{skill_id}/update")
    suspend fun updateWorkerSkill(
        @Header("Authorization") token: String,
        @Path("skill_id") id: Int,
        @Body workerSkill: WorkerSkillRequest
    ): WorkerSkillResponse

    @DELETE("/worker/skills/{skill_id}/delete")
    suspend fun deleteWorkerSkill(
        @Header("Authorization") token: String,
        @Path("skill_id") id: Int
    ): WorkerSkillResponse

    @GET("/worker/verify/with_email/")
    suspend fun verifyWorkerByEmail(
        @Header("Authorization") token: String,
        @Path("worker_profile_id") id: Int,
    ): WorkerProfileResponse

    @GET("/worker/verify/with_phone/")
    suspend fun verifyWorkerByPhone(
        @Header("Authorization") token: String,
        @Path("worker_profile_id") id: Int,
    ): WorkerProfileResponse

    @POST("/worker/verify/{code}")
    suspend fun verifyWorkerByCode(
        @Header("Authorization") token: String,
        @Path("worker_profile_id") id: Int,
    ): WorkerProfileResponse



}