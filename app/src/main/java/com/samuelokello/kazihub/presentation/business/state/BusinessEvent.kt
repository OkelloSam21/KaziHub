package com.samuelokello.kazihub.presentation.business.state

import com.google.android.libraries.places.api.model.AutocompletePrediction

sealed interface BusinessEvent {
    data class OnEmailChanged(val email: String): BusinessEvent
    data class OnPhoneNumberChanged(val phone: String): BusinessEvent
    data class OnLocationChanged(val location: String): BusinessEvent
    data class OnLocationInputChanged(val input: String): BusinessEvent
    data class OnLocationSuggestionsFetched(val suggestions: List<AutocompletePrediction>): BusinessEvent
    data class OnCreateProfileClicked(
        val email: String,
        val phone: String,
        val location: String,
        val bio: String
    ): BusinessEvent

    data class OnBioChanged(val bio: String) : BusinessEvent
}