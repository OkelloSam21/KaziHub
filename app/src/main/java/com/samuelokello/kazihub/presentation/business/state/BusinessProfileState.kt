package com.samuelokello.kazihub.presentation.business.state

import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.samuelokello.kazihub.presentation.common.AuthState

data class BusinessProfileState(
    override val isLoading: Boolean = false,
    override val error: String = "",
    val email: String = "sam@example.com",
    val phone: String = "0759876321",
    val bio: String = "bio",
    val location: String = "Tuuti",
    val locationLatLng: LatLng? = null,
    val placeId: String = "",
    val locationSuggestion: List<Place> = listOf(),
    val navigateToHome: Boolean = false
): AuthState

