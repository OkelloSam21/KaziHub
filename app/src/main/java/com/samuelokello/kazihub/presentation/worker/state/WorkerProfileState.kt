package com.samuelokello.kazihub.presentation.worker.state

import com.google.android.gms.maps.model.LatLng
import com.samuelokello.kazihub.presentation.common.AuthState

data class WorkerProfileState(
    override val isLoading: Boolean = false,
    override val error: String = "",
    val successMessage: String = "",
    val email: String = "",
    val phone: String = "",
    val bio: String = "",
    val location: String = "",
    val locationSuggestion: List<LatLng?> = emptyList(),
    val navigateToHome: Boolean = false
): AuthState