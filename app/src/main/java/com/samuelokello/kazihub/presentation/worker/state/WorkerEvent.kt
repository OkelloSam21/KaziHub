package com.samuelokello.kazihub.presentation.worker.state

sealed interface WorkerEvent {
    data class OnEmailChanged(val email: String): WorkerEvent
    data class OnPhoneNumberChanged(val phone: String): WorkerEvent
    data class OnLocationChanged(val location: String): WorkerEvent
    data class OnCreateProfileClicked(
        val email: String,
        val phone: String,
        val location: String,
        val bio: String
    ): WorkerEvent

    data class OnBioChanged(val bio: String) : WorkerEvent
}