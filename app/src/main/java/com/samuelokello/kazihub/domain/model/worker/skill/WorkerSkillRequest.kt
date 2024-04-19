package com.samuelokello.kazihub.domain.model.worker.skill

data class WorkerSkillRequest(
    val skill: List<String>? = emptyList(),
)
