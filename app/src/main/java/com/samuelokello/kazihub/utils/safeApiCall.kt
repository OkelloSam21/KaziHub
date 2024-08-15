package com.samuelokello.kazihub.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
    return withContext(Dispatchers.IO) {
        try {
            Resource.Success(apiCall.invoke())
        } catch (throwable: HttpException) {
            when (throwable.code()) {
                401 -> Resource.Error("Unauthorized. Please log in again.")
                403 -> Resource.Error("Forbidden. You don't have permission to access this resource.")
                404 -> Resource.Error("Resource not found.")
                else -> Resource.Error("An error occurred: ${throwable.message()}")
            }
        } catch (throwable: Throwable) {
            Resource.Error(throwable.localizedMessage ?: "An unexpected error occurred")
        }
    }
}