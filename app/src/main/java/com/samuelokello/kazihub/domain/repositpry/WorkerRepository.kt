package com.samuelokello.kazihub.domain.repositpry

import com.samuelokello.kazihub.domain.model.worker.WorkerProfileRequest
import com.samuelokello.kazihub.domain.model.worker.WorkerProfileResponse
import com.samuelokello.kazihub.domain.model.worker.image.WorkerProfileImageResponse
import com.samuelokello.kazihub.domain.model.worker.skill.WorkerSkillRequest
import com.samuelokello.kazihub.domain.model.worker.skill.WorkerSkillResponse
import com.samuelokello.kazihub.utils.Resource

interface WorkerRepository {
    suspend fun createWorkerProfile(request: WorkerProfileRequest): Resource<WorkerProfileResponse>
    suspend fun fetchWorkerProfileById(id: Int): Resource<WorkerProfileResponse>
    suspend fun fetchWorkerProfiles(): Resource<List<WorkerProfileResponse>>
    suspend fun updateWorkerProfile(id: Int, request: WorkerProfileRequest): Resource<WorkerProfileResponse>
    suspend fun fetchWorkerImage(id: Int): Resource<WorkerProfileImageResponse>
    suspend fun createSkills(request: WorkerSkillRequest): Resource<WorkerSkillResponse>
    suspend fun deleteSkill(id: Int): Resource<WorkerSkillResponse>
    suspend fun verifyWorkerWithEmail(email: String) : Resource<WorkerProfileResponse>
    suspend fun verifyWorkerWithPhone(phone: String) : Resource<WorkerProfileResponse>
    suspend fun verifyWorkerWithCode(code: String) : Resource<WorkerProfileResponse>
}
