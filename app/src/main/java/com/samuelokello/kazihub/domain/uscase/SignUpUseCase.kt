package com.samuelokello.kazihub.domain.uscase

import com.samuelokello.kazihub.domain.model.shared.auth.sign_up.SignUpRequest
import com.samuelokello.kazihub.domain.model.shared.auth.sign_up.SignUpResponse
import com.samuelokello.kazihub.domain.repositpry.AuthRepository
import com.samuelokello.kazihub.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(request: SignUpRequest): Flow<Resource<SignUpResponse>> = flow {
        emit(Resource.Loading())
        val result = repository.signUp(request)
        emit(result)
    }
}