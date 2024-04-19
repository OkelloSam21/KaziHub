package com.samuelokello.kazihub.presentation.worker.state

import com.samuelokello.kazihub.presentation.common.AuthState

data class WorkerProfileState(
    override val isLoading: Boolean = false,
    override val error: String = "",
    val email: String = "",
    val phone: String = "",
    val bio: String = "",
    val location: String = "",
    val latitude: Number = 0.0,
    val longitude: Number = 0.0,
    val navigateToHome: Boolean = false
): AuthState