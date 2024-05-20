package com.samuelokello.kazihub.presentation.worker.state

import com.google.android.libraries.places.api.model.Place
import com.samuelokello.kazihub.presentation.common.AuthState

data class WorkerProfileState(
    override val isLoading: Boolean = false,
    override val error: String = "",
    val successMessage: String = "",
    val email: String = "sam@Example.com",
    val phone: String = "0759876321",
    val bio: String = "some bio",
    val location: String = "",
    val locationSuggestion: List<Place> = emptyList(),
    val selectedLocation: Place = Place.builder().build(),
    val navigateToHome: Boolean = false,
) : AuthState
