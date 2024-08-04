package com.samuelokello.kazihub.domain.repositpry

import com.samuelokello.kazihub.data.model.profile.ProfileResponse
import com.samuelokello.kazihub.data.model.sign_in.SignInRequest
import com.samuelokello.kazihub.data.model.sign_in.SignInResponse
import com.samuelokello.kazihub.domain.model.shared.auth.sign_up.SignUpRequest
import com.samuelokello.kazihub.domain.model.shared.auth.sign_up.SignUpResponse
import com.samuelokello.kazihub.utils.Resource
import com.samuelokello.kazihub.utils.UserRole

interface AuthRepository {
    suspend fun signUp(signUpRequest: SignUpRequest): Resource<SignUpResponse>
    suspend fun signIn(signInRequest: SignInRequest): Resource<SignInResponse>
    suspend fun getCurrentUser(): Resource<ProfileResponse>
    suspend fun refreshToken(): Resource<SignInResponse>
    suspend fun signOut()
    suspend fun isUserLoggedIn(): Boolean
    suspend fun getUserType(): UserRole
}
