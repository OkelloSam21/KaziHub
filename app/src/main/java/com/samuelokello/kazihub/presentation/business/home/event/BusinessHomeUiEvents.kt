package com.samuelokello.kazihub.presentation.business.home.event

sealed interface BusinessHomeUiEvents {
    data class OnJobClick(val id: Int): BusinessHomeUiEvents
    data object OnFABClick: BusinessHomeUiEvents
    data object OnDrawerClick: BusinessHomeUiEvents
}