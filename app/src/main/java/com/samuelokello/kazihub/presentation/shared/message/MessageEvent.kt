package com.samuelokello.kazihub.presentation.shared.message

sealed interface MessageEvent {
    data class OnMessageItemClick(val message: Int): MessageEvent
}