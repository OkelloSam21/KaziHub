package com.samuelokello.kazihub.data.remote

import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileRequest
import com.samuelokello.kazihub.domain.model.Bussiness.BusinessProfileResponse
import com.samuelokello.kazihub.domain.model.JobsResponse
import com.samuelokello.kazihub.domain.model.sign_in.SignInRequest
import com.samuelokello.kazihub.domain.model.sign_in.SignInResponse
import com.samuelokello.kazihub.domain.model.sign_up.SignUpRequest
import com.samuelokello.kazihub.domain.model.sign_up.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface KaziHubApi {
    @POST("auth/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): SignUpResponse

    @POST("auth/signin")
    suspend fun sigIn(@Body signInRequest: SignInRequest): SignInResponse

    @POST("/business/profiles/create")
    suspend fun createBusinessProfile(
        @Header("Authorization") token: String,
        @Body businessProfile: BusinessProfileRequest
    ): BusinessProfileResponse

    @GET("/business/profiles/{bus_profile_id}")
    suspend fun getBusinessProfile(
        @Path("bus_profile_id") id : Int
    ): BusinessProfileResponse

    @GET("/business/profiles/all/")
    suspend fun getAllBusinessProfiles(): List<BusinessProfileResponse>

    @PUT("/business/profiles/{bus_profile_id}/update")
    suspend fun updateBusinessProfile(
        @Path("bus_profile_id") id : Int,
        @Body businessProfile: BusinessProfileRequest
    ): BusinessProfileResponse

    @PUT("/business/profiles/{bus_profile_id}/update/image")
    suspend fun updateBusinessProfileImage(
        @Path("bus_profile_id") id : Int,
        @Body businessProfile: BusinessProfileRequest
    ): BusinessProfileResponse

    @GET("/business/verify/with_email/")
    suspend fun verifyBusinessByEmail(
        @Path("bus_profile_id") id : Int,
        @Body businessProfile: BusinessProfileRequest
    ): BusinessProfileResponse

    @GET("/business/verify/with_phone/")
    suspend fun verifyBusinessByPhone(
        @Path("bus_profile_id") id : Int,
        @Body businessProfile: BusinessProfileRequest
    ): BusinessProfileResponse

    @GET("/business/profile/verify/")
    suspend fun verifyBusinessByCode(
        @Path("bus_profile_id") id : Int,
        @Body businessProfile: BusinessProfileRequest
    ): BusinessProfileResponse



    @GET("jobs/list")
    suspend fun getJobs(): List<JobsResponse>


//    @GET("jobs/categories")

    /**
     * Post /jobs/categories/create
     * Get /jobs/categories/list
     * Get /jobs/categories/{category_id}
     * Post /jobs/create
     * Get /jobs/list
     * Get /jobs/{job_id}
     * Get /jobs/list/category/{category_id}
     * Get /jobs/list/business/{business_id}
     * Put /jobs/update/{job_id}
     * Delete /jobs/delete/{job_id}
     * Put /jobs/update/status/{job_id}
     * Get /jobs/list/nearby{lat}/{lon}
     * Get /jobs/list/search/{q}
     * Get /jobs/filter
     * Get /jobs/recent
     *
     */
}