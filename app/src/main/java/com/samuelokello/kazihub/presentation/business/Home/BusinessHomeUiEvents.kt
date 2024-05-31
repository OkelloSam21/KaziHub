package com.samuelokello.kazihub.presentation.business.Home

sealed interface BusinessHomeUiEvents {
    data class OnJobClick(val id: Int): BusinessHomeUiEvents
    data object OnCreateJobClick: BusinessHomeUiEvents
    data object OnDrawerClick: BusinessHomeUiEvents
}