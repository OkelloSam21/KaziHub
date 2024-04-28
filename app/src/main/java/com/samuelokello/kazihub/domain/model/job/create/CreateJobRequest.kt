package com.samuelokello.kazihub.domain.model.job.create

data class CreateJobRequest(
    val title: String,
    val description: String,
    val budget: Int,
    val location: String,
    val categoryId: Int,
    val qualifications: String
)

