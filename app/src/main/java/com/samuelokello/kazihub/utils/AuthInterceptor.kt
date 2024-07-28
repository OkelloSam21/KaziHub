package com.samuelokello.kazihub.utils


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
        val requestBuilder = originalRequest.newBuilder()

        if (tokenManager.hasAccessToken()) {
            requestBuilder.addHeader("Authorization", "Bearer ${tokenManager.getAccessToken()}")
        }

        val request = requestBuilder.build()
        val response = chain.proceed(request)

        // If the response is 401 Unauthorized, try to refresh the token
        if (response.code == 401 && tokenManager.hasRefreshToken()) {
            response.close()
            val newAccessToken = refreshToken() // Implement this method to refresh the token
            if (newAccessToken != null) {
                val newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $newAccessToken")
                    .build()
                return chain.proceed(newRequest)
            }
        }

        return response
    }

    private fun refreshToken(): String? {
        // Implement token refresh logic here
        // This should make a network call to refresh the token
        // Update TokenManager with the new tokens
        // Return the new access token or null if refresh failed
        return null
    }
}