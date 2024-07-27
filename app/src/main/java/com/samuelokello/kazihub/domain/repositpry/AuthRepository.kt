package com.samuelokello.kazihub.domain.repositpry

import com.samuelokello.kazihub.data.model.sign_in.SignInRequest
import com.samuelokello.kazihub.data.model.sign_in.SignInResponse
import com.samuelokello.kazihub.domain.model.shared.auth.sign_up.SignUpRequest
import com.samuelokello.kazihub.domain.model.shared.auth.sign_up.SignUpResponse
import com.samuelokello.kazihub.utils.Resource

interface AuthRepository {
    suspend fun signUp(signUpRequest: SignUpRequest): Resource<SignUpResponse>
    suspend fun signIn(signInRequest: SignInRequest): Resource<SignInResponse>
    suspend fun checkProfile(): Resource<Boolean>
}
