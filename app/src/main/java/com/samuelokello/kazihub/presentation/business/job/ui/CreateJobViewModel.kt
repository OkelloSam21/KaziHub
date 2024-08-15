package com.samuelokello.kazihub.presentation.business.job.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.places.api.model.Place
import com.samuelokello.kazihub.domain.model.job.category.Category
import com.samuelokello.kazihub.domain.model.job.category.FetchCategoryResponse
import com.samuelokello.kazihub.domain.model.job.create.CreateJobRequest
import com.samuelokello.kazihub.domain.repositpry.AuthRepository
import com.samuelokello.kazihub.domain.repositpry.JobRepository
import com.samuelokello.kazihub.presentation.business.job.event.CreateJobUiEvent
import com.samuelokello.kazihub.presentation.business.job.state.CreateJobUiState
import com.samuelokello.kazihub.utils.LocationManager
import com.samuelokello.kazihub.utils.Resource
import com.samuelokello.kazihub.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateJobViewModel @Inject constructor(
    private val repository: JobRepository,
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager,
    @ApplicationContext private val context: Context,
    private val locationManager: LocationManager,
) : ViewModel() {

    private val _createDataRequest = MutableStateFlow(CreateJobUiState())
    val createJobState = _createDataRequest.asStateFlow()

    private val _categories = MutableStateFlow(FetchCategoryResponse(0, emptyList(), "", ""))
    val categories = _categories.asStateFlow()

    private val _selectedCategory = MutableStateFlow(Category(0, "Select Category"))
    val selectedCategory = _selectedCategory.asStateFlow()

    init {
        fetchJobCategory()
    }

    fun onJobEvent(event: CreateJobUiEvent) {
        when (event) {
            is CreateJobUiEvent.OnCreateJobClick -> onCreateJobClick()
            is CreateJobUiEvent.OnBudgetChange -> updateState { it.copy(budget = event.budget) }
            is CreateJobUiEvent.OnDescriptionChange -> updateState { it.copy(description = event.description) }
            is CreateJobUiEvent.OnLocationChange -> onLocationChange(event.location)
            is CreateJobUiEvent.OnQualificationsChange -> updateState { it.copy(qualifications = event.qualifications.joinToString(",")) }
            is CreateJobUiEvent.OnTitleChange -> updateState { it.copy(title = event.title) }
            is CreateJobUiEvent.OnCategoryChange -> {
                updateState { it.copy(categoryId = event.item.id) }
                _selectedCategory.value = event.item
            }
            is CreateJobUiEvent.OnSuggestionSelected -> onSuggestionSelected(event.suggestion)
        }
    }

    private fun onCreateJobClick() {
        viewModelScope.launch {
            if (tokenManager.hasAccessToken() && tokenManager.hasRefreshToken()) {
                refreshTokenIfNeeded()
            }
            val request = CreateJobRequest(
                title = _createDataRequest.value.title,
                description = _createDataRequest.value.description,
                budget = _createDataRequest.value.budget,
                category_id = _createDataRequest.value.categoryId,
                location = _createDataRequest.value.location,
                qualifications = _createDataRequest.value.qualifications
            )

            _createDataRequest.update { it.copy(isLoading = true) }

            when (val result = repository.createJob(request)) {
                is Resource.Success -> {
                    _createDataRequest.update { it.copy(isLoading = false) }
                    Log.e("CreateJobViewModel", "code : ${result.data?.code} message : ${result.data?.message}")
                    showToast("Job created successfully")
                }
                is Resource.Error -> {
                    if (result.message?.contains("Unauthorized") == true) {
                        if (refreshTokenIfNeeded()) {
                            onCreateJobClick()
                        }
                    }
                    _createDataRequest.update { it.copy(isLoading = false) }
                    showToast(result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    // This state is handled at the beginning of the function
                    _createDataRequest.update { it.copy(isLoading = true) }
                }
            }
        }
    }
    private suspend fun refreshTokenIfNeeded(): Boolean {
        return when (val result = authRepository.refreshToken()) {
            is Resource.Success -> {
                result.data?.let { tokens ->
                    tokenManager.setToken(
                        access = tokens.data?.accessToken ?: "",
                        refresh = tokens.data?.refreshToken ?: ""
                    )
                }
                true
            }
            else -> false
        }
    }


    private fun onLocationChange(location: String) {
        updateState { it.copy(location = location) }
        val searchJob = Job()
        searchJob.cancelChildren()
        viewModelScope.launch(Dispatchers.Main + searchJob) {
            delay(300)
            locationManager.fetchLocationSuggestions(
                location,
                onSuccess = { suggestions -> updateState { it.copy(locationSuggestion = suggestions) } },
                onError = { updateState { it.copy(locationSuggestion = emptyList()) } }
            )
        }
    }

    private fun onSuggestionSelected(place: Place) {
        updateState {
            it.copy(
                location = place.name?.toString() ?: "",
                selectedLocation = place
            )
        }
    }

    private fun fetchJobCategory() {
        viewModelScope.launch {
            when (val response = repository.fetchJobCategory()) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    _categories.value = response.data?: FetchCategoryResponse(0, emptyList(), "", "")
                    Log.d("CreateJobViewModel", "Categories: ${response.data?.data}")
                }
                is Resource.Error -> {
                    showToast(response.message ?: "An error occurred")

                }
            }
        }
    }

    private fun updateState(update: (CreateJobUiState) -> CreateJobUiState) {
        _createDataRequest.update(update)
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}