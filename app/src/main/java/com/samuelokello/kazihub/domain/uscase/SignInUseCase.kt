package com.samuelokello.kazihub.domain.uscase

import com.samuelokello.kazihub.data.model.sign_in.SignInRequest
import com.samuelokello.kazihub.data.model.sign_in.SignInResponse
import com.samuelokello.kazihub.domain.repositpry.AuthRepository
import com.samuelokello.kazihub.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, password: String): Flow<Resource<SignInResponse>> = flow {
        emit(Resource.Loading())
        val result = repository.signIn(SignInRequest(email, password))
        emit(result)
    }
}