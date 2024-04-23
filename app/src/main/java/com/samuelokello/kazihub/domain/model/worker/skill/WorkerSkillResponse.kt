package com.samuelokello.kazihub.domain.model.worker.skill

import com.google.gson.annotations.SerializedName

data class WorkerSkillResponse(
    @SerializedName("skill")
    val skill: List<String>?,
)
