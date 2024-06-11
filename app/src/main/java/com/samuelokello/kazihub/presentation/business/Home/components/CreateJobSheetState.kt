package com.samuelokello.kazihub.presentation.business.Home.components

data class CreateJobSheetState (
    val title: String = "",
    val description: String = "",
    val budget: Int = 0,
    val location: String = "",
    val category: String = "",
    val qualification: List<String> = emptyList()
)
