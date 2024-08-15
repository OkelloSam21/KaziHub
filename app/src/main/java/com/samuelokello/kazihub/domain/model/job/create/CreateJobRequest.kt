package com.samuelokello.kazihub.domain.model.job.create

data class CreateJobRequest(
    val title: String,
    val description: String,
    val budget: String,
    val location: String,
    val category_id: Int,
    val qualifications: String
)

