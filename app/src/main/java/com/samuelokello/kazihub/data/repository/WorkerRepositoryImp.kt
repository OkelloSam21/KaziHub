package com.samuelokello.kazihub.data.repository

import com.samuelokello.kazihub.data.remote.KaziHubApi
import com.samuelokello.kazihub.domain.model.worker.WorkerProfileRequest
import com.samuelokello.kazihub.domain.model.worker.WorkerProfileResponse
import com.samuelokello.kazihub.domain.model.worker.image.WorkerProfileImageResponse
import com.samuelokello.kazihub.domain.model.worker.skill.WorkerSkillRequest
import com.samuelokello.kazihub.domain.model.worker.skill.WorkerSkillResponse
import com.samuelokello.kazihub.domain.repositpry.WorkerRepository
import com.samuelokello.kazihub.utils.Resource
import com.samuelokello.kazihub.utils.safeApiCall
import javax.inject.Inject

class WorkerRepositoryImp
@Inject constructor(
    private val api: KaziHubApi,
   ) : WorkerRepository {
    override suspend fun createWorkerProfile(request: WorkerProfileRequest): Resource<WorkerProfileResponse> =
        safeApiCall { api.createWorkerProfile(request) }

    override suspend fun fetchWorkerProfileById(id: Int): Resource<WorkerProfileResponse> =
        safeApiCall { api.getWorkerProfile(id) }

    override suspend fun fetchWorkerProfiles(): Resource<List<WorkerProfileResponse>> =
        safeApiCall { api.getAllWorkerProfiles() }


    override suspend fun updateWorkerProfile(
        id: Int,
        request: WorkerProfileRequest
    ): Resource<WorkerProfileResponse> = safeApiCall { api.updateWorkerProfile(id, request) }

    override suspend fun fetchWorkerImage(id: Int): Resource<WorkerProfileImageResponse> =
        safeApiCall { api.getWorkerProfileImage(id) }

    override suspend fun createSkills(request: WorkerSkillRequest): Resource<WorkerSkillResponse> =
        safeApiCall { api.createWorkerSkill(request) }

    override suspend fun deleteSkill(id: Int): Resource<WorkerSkillResponse> =
        safeApiCall { api.deleteWorkerSkill(id) }

    override suspend fun verifyWorkerWithEmail(email: String): Resource<WorkerProfileResponse> =
        safeApiCall { api.verifyWorkerByEmail(email) }

    override suspend fun verifyWorkerWithPhone(phone: String): Resource<WorkerProfileResponse> =
        safeApiCall { api.verifyWorkerByPhone(phone) }

    override suspend fun verifyWorkerWithCode(code: String): Resource<WorkerProfileResponse> =
        safeApiCall { api.verifyWorkerByCode(code) }

}
