package com.samuelokello.kazihub.presentation.business.home.event

import com.google.android.libraries.places.api.model.Place

sealed interface BusinessEvent {
    data class OnEmailChanged(val email: String): BusinessEvent
    data class OnPhoneNumberChanged(val phone: String): BusinessEvent
    data class OnLocationChanged(val location: String): BusinessEvent
    class OnSuggestionSelected(val suggestion: Place) : BusinessEvent
    data class OnCreateProfileClicked(
        val email: String,
        val phone: String,
        val location: String,
        val bio: String,
        val selectedLocation: Place
    ): BusinessEvent

    data class OnBioChanged(val bio: String) : BusinessEvent

}