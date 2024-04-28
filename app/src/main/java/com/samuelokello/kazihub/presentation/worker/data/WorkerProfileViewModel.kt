package com.samuelokello.kazihub.presentation.worker.data

import androidx.lifecycle.ViewModel
import com.samuelokello.kazihub.data.repository.KaziHubRepository
import com.samuelokello.kazihub.presentation.worker.state.WorkerProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class WorkerProfileViewModel
@Inject constructor(private val repository: KaziHubRepository) : ViewModel() {

    private val _state = MutableStateFlow(WorkerProfileState())
    val state = _state.asStateFlow()

//    fun onEvent(event: WorkerProfileEvent) {
//        when (event) {
//            is WorkerProfileEvent.OnBioChanged -> {
//                _state.update { it.copy(bio = event.bio) }
//            }
//            is WorkerProfileEvent.OnCreateProfileClicked -> {
//                val request = WorkerProfileRequest(
//                    bio = state.value.bio
//                )
//                updateWorkerProfile(request, event.id)
//            }
//        }
//    }

    // Function to update worker profile
//    fun updateWorkerProfile(request: WorkerProfileRequest, id: Int) {
//        viewModelScope.launch {
//            when (val response = repository.updateWorkerProfile(id, request)) {
//                is Resource.Success -> {
//                    // Handle success case
//                    _state.update { it.copy(successMessage = "Profile updated successfully!") }
//                }
//
//                is Resource.Error -> {
//                    // Handle error case
//                    _state.update {
//                        it.copy(
//                            error = response.message ?: "Failed to update profile"
//                        )
//                    }
//                }
//            }
//        }
//    }
}