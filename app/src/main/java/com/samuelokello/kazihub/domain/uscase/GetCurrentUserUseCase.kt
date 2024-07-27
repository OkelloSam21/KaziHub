package com.samuelokello.kazihub.domain.uscase

import com.samuelokello.kazihub.data.model.profile.ProfileResponse
import com.samuelokello.kazihub.domain.repositpry.AuthRepository
import com.samuelokello.kazihub.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Flow<Resource<ProfileResponse>> = flow {
        emit(Resource.Loading())
        val result = repository.getCurrentUser()
        emit(result)
    }
}