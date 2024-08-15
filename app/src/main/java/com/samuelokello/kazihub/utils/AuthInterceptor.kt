package com.samuelokello.kazihub.utils


import android.util.Log
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

// AuthInterceptor.kt
class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    private val noAuthEndpoints = listOf(
        "/auth/signin",
        "/auth/signup",
        "/auth/token/refresh",
        "/auth/users/all",
        "/auth/users/{username}",
        "/auth/users/id/{id}",
        "/auth/forgot-password",
        "/business/profiles/{bus_profile_id}",
        "/business/profiles/all",
        "/business/profiles/image/{profile_id}",
        "/worker/profiles/all",
        "/worker/profiles/{worker_profile_id}",
        "/worker/profiles/image/{worker_profile_id}",
        "/worker/skills/worker/{worker_profile_id}",
        "/jobs/category/create",
        "/jobs/category/list",
        "/jobs/category/{category_id}",
        "/jobs/list",
        "/jobs/list/{job_id}",
        "/jobs/list/category/{category_id}",
        "/jobs/list/business/{business_id}",
        "/jobs/list/nearby/{lat}/{lon}",
        "/jobs/list/search/{q}",
        "/jobs/filter",
        "/jobs/recent",
        "/proposals/",
        "/test-email"
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Check if the request needs authentication
        if (!noAuthEndpoints.contains(originalRequest.url.encodedPath)) {
            // Add the Authorization header with the Bearer token
            if (tokenManager.hasAccessToken()) {
                val newRequest = originalRequest.newBuilder()
                    .addHeader("Authorization", "Bearer ${tokenManager.getAccessToken()}")
                    .build()
                return chain.proceed(newRequest)
            } else {
                Log.e("CreateJob" ,"Access token missing or invalid")
            }
        }

        return chain.proceed(originalRequest)
    }


    private fun refreshToken(): String? {
        val response = runBlocking {
            tokenManager.getRefreshToken()
        }
//        return when (response) {
////            is Resource.Success -> response.data?.data?.accessToken
//
//            else -> null
//        }
        return null
    }

}