package com.samuelokello.kazihub.presentation.business.home.state

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.places.api.model.Place
import com.samuelokello.kazihub.domain.model.job.category.CategoryResponse
import com.samuelokello.kazihub.domain.model.job.create.CreateJobRequest
import com.samuelokello.kazihub.domain.repositpry.JobRepository
import com.samuelokello.kazihub.presentation.business.home.components.CreateJobUiEvent
import com.samuelokello.kazihub.presentation.business.home.components.CreateJobUiState
import com.samuelokello.kazihub.utils.LocationManager
import com.samuelokello.kazihub.utils.Resource
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
    private val repository: JobRepository, // Injecting the JobRepository to interact with the data layer
    @ApplicationContext private val context: Context, // Injecting the application context
    private val locationManager: LocationManager,
) : ViewModel(){

    init {
        fetchJobCategory() // Fetching data categories when ViewModel is initialized
    }

    // MutableStateFlow to hold the state of the Create data form
    private val _createDataRequest = MutableStateFlow(CreateJobUiState())
    val createJobState = _createDataRequest.asStateFlow() // Exposing an immutable StateFlow for observing the state

    // MutableStateFlow to hold the list of data categories
    private val _categories = MutableStateFlow<List<CategoryResponse>>(emptyList())
    val categories = _categories.asStateFlow() // Exposing an immutable StateFlow for observing the categories

    private val _selectedCategory = MutableStateFlow<CategoryResponse?>(null)
    val selectedCategory = _selectedCategory.asStateFlow()

    fun onJobEvent(event: CreateJobUiEvent) {
        when (event) {
            is CreateJobUiEvent.OnCreateJobClick -> onCreateJobClick()
            is CreateJobUiEvent.OnBudgetChange -> onBudgetChange(event.budget)
            is CreateJobUiEvent.OnDescriptionChange -> onDescriptionChange(event.description)
            is CreateJobUiEvent.OnLocationChange -> onLocationChange(event.location)
            is CreateJobUiEvent.OnQualificationsChange -> onQualificationsChange(event.qualifications)
            is CreateJobUiEvent.OnTitleChange -> onTitleChange(event.title)
            is CreateJobUiEvent.OnCategoryChange -> {}
            is CreateJobUiEvent.OnSuggestionSelected -> onSuggestionSelected(event.suggestion)
        }
    }

    // Function to handle the Create data button click
    private fun onCreateJobClick() {
        viewModelScope.launch {
            // Extracting the form data from the state
            val title = _createDataRequest.value.title
            val description = _createDataRequest.value.description
            val budget = _createDataRequest.value.budget
            val location = _createDataRequest.value.location
            val categoryId = _createDataRequest.value.categoryId
            val qualifications = _createDataRequest.value.qualifications

            // Creating the CreateJobRequest object
            val request = CreateJobRequest(
                title = title,
                description = description,
                budget = budget,
                categoryId = categoryId,
                location = location,
                qualifications = qualifications.toString()
            )

            // Making the API call to create a data
            when (val response = repository.createJob(request)) {
                is Resource.Loading -> {
                    // Handling the loading state
                }
                is Resource.Success -> {
                    // Handling the success response
                    val successMessage = response.message
                    Toast.makeText(
                        context,
                        successMessage ?: "data created successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is Resource.Error -> {
                    // Handling the error response
                    val error = response.message
                    Toast.makeText(context, error ?: "An error occurred", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Functions to handle changes in the form fields
    private fun onBudgetChange(budget: Int) {
        _createDataRequest.update {
            it.copy(budget = budget)
        }
    }

    private fun onDescriptionChange(description: String) {
        _createDataRequest.update {
            it.copy(description = description)
        }
    }

    private fun onLocationChange(location: String) {
        _createDataRequest.update {
            it.copy(location = location)
        }

        val searchJob = Job()
        searchJob.cancelChildren()
        viewModelScope.launch(Dispatchers.Main + searchJob) {
            delay(300)
            locationManager.fetchLocationSuggestions(
                location,
                onSuccess = { suggestions ->
                    _createDataRequest.update {
                        it.copy(locationSuggestion = suggestions)
                    }
                },
                onError = {
                    // Handle error
                    _createDataRequest.update {
                        it.copy(
                            locationSuggestion = emptyList(),
                        )
                    }
                }
            )

        }
    }

    private fun onSuggestionSelected(place: Place) {
        _createDataRequest.update {
            it.copy(
                location = place.name?.toString() ?: "",
                selectedLocation = place)
        }
    }

    private fun onQualificationsChange(qualifications: String) {
        _createDataRequest.update {
            it.copy(qualifications = listOf(qualifications))
        }
    }

    private fun onTitleChange(title: String) {
        _createDataRequest.update {
            it.copy(title = title)
        }
    }

    private fun onCategoryChange(category: CategoryResponse) {
        _selectedCategory.value = category
        _createDataRequest.update {
            it.copy(categoryId = category.id!!)
        }
    }

    // Function to fetch data categories from the API
    private fun fetchJobCategory() {
        viewModelScope.launch {
            when (val response = repository.fetchJobCategory()) {
                is Resource.Loading -> {
                    // Handling the loading state
                }
                is Resource.Success -> {
                    // Updating the _categories state with the fetched categories
                    _categories.value = listOf(response.data!!)
                }

                is Resource.Error -> {
                    // Handling the error response
                }
            }
        }
    }
}