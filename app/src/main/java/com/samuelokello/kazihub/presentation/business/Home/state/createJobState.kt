package com.samuelokello.kazihub.presentation.business.Home.state

data class createJobState(
    val title: String,
    val description: String,
    val budget: Int,
    val location: String,
    val categoryId: Int,
    val qualifications: List<String>
)
