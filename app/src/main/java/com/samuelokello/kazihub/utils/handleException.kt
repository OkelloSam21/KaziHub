package com.samuelokello.kazihub.utils

import retrofit2.HttpException

fun <T> handleException(e: Exception): Resource<T> {
    return if (e is HttpException && (e.hashCode() == 401 || e.hashCode() == 403)) {
        Resource.Error("Authorization failed. Please check your credentials.")
    } else {
        Resource.Error(e.message ?: "An error occurred")
    }
}