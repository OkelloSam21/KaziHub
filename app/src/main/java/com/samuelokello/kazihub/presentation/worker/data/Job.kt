package com.samuelokello.kazihub.presentation.worker.data

import com.samuelokello.kazihub.domain.model.job.Business
import com.samuelokello.kazihub.domain.model.job.Category
import com.samuelokello.kazihub.domain.model.job.Status

data class Job(
    val id: Int?,
    val title: String?,
    val desc: String?,
    val budget: Int?,
    val location: String?,
    val postedOn: String?,
    val status: Status?,
    val business: Business?,
    val businessId: Int?,
    val category: Category?,
    val categoryId: Int?,
    val imageUrl: String? = null,
    val time: String? = null
)