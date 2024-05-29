package com.samuelokello.kazihub.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.kazihub.domain.repositpry.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LandingScreenState (val destination: Destination? = null) {
    enum class Destination{
        SIGN_IN,
        ON_BOARDING,
        CREATE_PROFILE,
        SIGN_UP,
        USER_TYPE,
        HOME
    }
}
class LandingScreenViewModel @Inject constructor(val repository: AuthRepository):
    ViewModel() {

        private val _state = MutableStateFlow(LandingScreenState())
        val state = _state.asStateFlow()
        init {
            checkNavigationDestination()
        }

    private fun checkNavigationDestination() {
        viewModelScope.launch {
            val userHasOnBoarded = repository.userHasOnBoarded.first()
            if (userHasOnBoarded.not()) {
                _state.update { it.copy(destination = LandingScreenState.Destination.USER_TYPE) }
            }

            val hasSignedIn = repository.userHasSignedIn.first()
            if (hasSignedIn.not()) {
                _state.update { it.copy(destination = LandingScreenState.Destination.SIGN_IN) }
                return@launch
            }

            val hasSignedUp = repository.userHasSignedUp.first()
            if (hasSignedUp.not()) {
                _state.update { it.copy(destination = LandingScreenState.Destination.SIGN_UP) }
                return@launch
            }

            _state.update { it.copy(destination = LandingScreenState.Destination.HOME) }
        }
    }
}