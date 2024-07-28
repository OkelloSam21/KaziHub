package com.samuelokello.kazihub.utils

import javax.inject.Inject

// TokenManager.kt
class TokenManager @Inject constructor() {
    private var accessToken: String = ""
    private var refreshToken: String = ""

    fun setToken(access: String, refresh: String) {
        accessToken = access
        refreshToken = refresh
    }

    fun getAccessToken(): String = accessToken
    fun getRefreshToken(): String = refreshToken

    fun clearTokens() {
        accessToken = ""
        refreshToken = ""
    }

    fun hasAccessToken(): Boolean = accessToken.isNotBlank()
    fun hasRefreshToken(): Boolean = refreshToken.isNotBlank()
}