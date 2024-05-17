package com.samuelokello.kazihub.domain.repositpry

import com.samuelokello.kazihub.data.model.sign_in.SignInRequest
import com.samuelokello.kazihub.data.model.sign_in.SignInResponse
import com.samuelokello.kazihub.domain.model.sign_up.SignUpRequest
import com.samuelokello.kazihub.domain.model.sign_up.SignUpResponse
import com.samuelokello.kazihub.utils.Resource

interface KaziHubRepository {
    suspend fun signUp(signUpRequest: SignUpRequest): Resource<SignUpResponse>
    suspend fun signIn(signInRequest: SignInRequest): Resource<SignInResponse>
}
