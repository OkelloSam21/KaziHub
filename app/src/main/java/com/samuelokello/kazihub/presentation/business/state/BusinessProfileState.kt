package com.samuelokello.kazihub.presentation.business.state

import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.samuelokello.kazihub.presentation.common.AuthState

data class BusinessProfileState(
    override val isLoading: Boolean = false,
    val email: String = "",
    val phone: String = "",
    val bio: String = "",
    val location: String = "",
    val locationLatLng: LatLng? = null,
    val placeId: String = "",
    val locationSuggestion: List<AutocompletePrediction> = listOf(),
    val navigateToHome: Boolean = false
): AuthState

