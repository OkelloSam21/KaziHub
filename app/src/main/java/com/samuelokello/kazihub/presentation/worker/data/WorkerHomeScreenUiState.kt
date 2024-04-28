package com.samuelokello.kazihub.presentation.worker.data

import androidx.compose.material3.DrawerValue

data class WorkerHomeScreenUiState(
    val isLoading: Boolean = false,
    val drawerState: DrawerValue = DrawerValue.Closed,
    val isSearchBarActive: Boolean = false,
    val searchQuery: String = "",
    val selectedItemIndex: Int = 0,
    val data: List<Job?> = emptyList()
)