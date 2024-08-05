package com.samuelokello.kazihub.domain.model.job.fetchById


import com.google.gson.annotations.SerializedName

data class Qualification(
    @SerializedName("id")
    val id: Int,
    @SerializedName("job_id")
    val jobId: Int,
    @SerializedName("name")
    val name: String
)