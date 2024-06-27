package com.samuelokello.kazihub.presentation.business.Home.state

data class CreateJobState(
    val title: String = "",
    val description: String = "",
    val budget: Int = 0,
    val location: String = "",
    val categoryId: Int = 0,
    val category: String = "",
    val categories: List<String> = emptyList(),
    val qualifications: List<String> = emptyList()
)
