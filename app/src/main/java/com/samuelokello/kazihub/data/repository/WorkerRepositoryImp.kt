package com.samuelokello.kazihub.data.repository

import android.content.Context
import com.samuelokello.kazihub.data.remote.KaziHubApi
import com.samuelokello.kazihub.domain.model.worker.WorkerProfileRequest
import com.samuelokello.kazihub.domain.model.worker.WorkerProfileResponse
import com.samuelokello.kazihub.domain.model.worker.image.WorkerProfileImageResponse
import com.samuelokello.kazihub.domain.model.worker.skill.WorkerSkillRequest
import com.samuelokello.kazihub.domain.model.worker.skill.WorkerSkillResponse
import com.samuelokello.kazihub.domain.repositpry.WorkerRepository
import com.samuelokello.kazihub.utils.Resource
import com.samuelokello.kazihub.utils.getAccessToken
import com.samuelokello.kazihub.utils.handleException
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WorkerRepositoryImp
@Inject constructor(
    private val api: KaziHubApi,
    @ApplicationContext private val context: Context
) : WorkerRepository {
    private val token = getAccessToken(context)
    override suspend fun createWorkerProfile(request: WorkerProfileRequest): Resource<WorkerProfileResponse> {
        return try {
            val response = api.createWorkerProfile("Bearer $token", request)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun fetchWorkerProfileById(id: Int): Resource<WorkerProfileResponse> {
        return try {
            val response = api.getWorkerProfile(id)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun fetchWorkerProfiles(): Resource<List<WorkerProfileResponse>> {
        return try {
            val response = api.getAllWorkerProfiles()
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun updateWorkerProfile(
        id: Int,
        request: WorkerProfileRequest
    ): Resource<WorkerProfileResponse> {
        return try {
            val response = api.updateWorkerProfile("Bearer $token", id, request)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun fetchWorkerImage(id: Int): Resource<WorkerProfileImageResponse> {
        return try {
            val response = api.getWorkerProfileImage(id)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun createSkills(request: WorkerSkillRequest): Resource<WorkerSkillResponse> {
        return try {
            val response = api.createWorkerSkill(token = token, workerSkill = request)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun deleteSkill(id: Int): Resource<WorkerSkillResponse> {
        return try {
            val response = api.deleteWorkerSkill(token, id)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun verifyWorkerWithEmail(): Resource<WorkerProfileResponse> {
        return try {
            val response = api.verifyWorkerByEmail(token)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun verifyWorkerWithPhone(): Resource<WorkerProfileResponse> {
        return try {
            val response = api.verifyWorkerByPhone(token)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun verifyWorkerWithCode(): Resource<WorkerProfileResponse> {
        return try {
            val response = api.verifyWorkerByCode(token)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

}
