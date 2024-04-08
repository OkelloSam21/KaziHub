package com.samuelokello.kazihub.presentation.shared.search

import androidx.annotation.DrawableRes

data class JobDTO(
    @DrawableRes val profileImage:  Int,
    val title: String,
    val companyName: String,
    val position: String,
    val budget: String,
    val location: String,
    val timePosted: String
    )
{
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$title $companyName",
            "$companyName $title",
            "$position $location",
            "${title.first()} ${companyName.first()} ${position.first()} ",
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}