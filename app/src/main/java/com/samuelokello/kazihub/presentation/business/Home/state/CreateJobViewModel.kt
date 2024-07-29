package com.samuelokello.kazihub.presentation.business.Home.state

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.kazihub.domain.model.job.category.CategoryResponse
import com.samuelokello.kazihub.domain.model.job.create.CreateJobRequest
import com.samuelokello.kazihub.domain.repositpry.JobRepository
import com.samuelokello.kazihub.presentation.business.Home.components.CreateJobSheetState
import com.samuelokello.kazihub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateJobViewModel @Inject constructor(
    private val repository: JobRepository, // Injecting the JobRepository to interact with the data layer
    @ApplicationContext private val context: Context, // Injecting the application context
) : ViewModel(){

    init {
        fetchJobCategory() // Fetching job categories when ViewModel is initialized
    }

    // MutableStateFlow to hold the state of the Create Job form
    private val _createJobRequest = MutableStateFlow(CreateJobSheetState())
    val createJobState = _createJobRequest.asStateFlow() // Exposing an immutable StateFlow for observing the state

    // MutableStateFlow to hold the list of job categories
    private val _categories = MutableStateFlow<List<CategoryResponse>>(emptyList())
    val categories = _categories.asStateFlow() // Exposing an immutable StateFlow for observing the categories

    private val _selectedCategory = MutableStateFlow<CategoryResponse?>(null)
    val selectedCategory = _selectedCategory.asStateFlow()

    // Function to handle the Create Job button click
    fun onCreateJobClick() {
        viewModelScope.launch {
            // Extracting the form data from the state
            val title = _createJobRequest.value.title
            val description = _createJobRequest.value.description
            val budget = _createJobRequest.value.budget
            val location = _createJobRequest.value.location
            val categoryId = _createJobRequest.value.categoryId
            val qualifications = _createJobRequest.value.qualifications

            // Creating the CreateJobRequest object
            val request = CreateJobRequest(
                title = title,
                description = description,
                budget = budget,
                categoryId = categoryId,
                location = location,
                qualifications = qualifications.toString()
            )

            // Making the API call to create a job
            when (val response = repository.createJob(request)) {
                is Resource.Loading -> {
                    // Handling the loading state
                }
                is Resource.Success -> {
                    // Handling the success response
                    val successMessage = response.message
                    Toast.makeText(
                        context,
                        successMessage ?: "Job created successfully",
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
    fun onBudgetChange(budget: Int) {
        _createJobRequest.update {
            it.copy(budget = budget)
        }
    }

    fun onDescriptionChange(description: String) {
        _createJobRequest.update {
            it.copy(description = description)
        }
    }

    fun onLocationChange(location: String) {
        _createJobRequest.update {
            it.copy(location = location)
        }
    }

    fun onQualificationsChange(qualifications: String) {
        _createJobRequest.update {
            it.copy(qualifications = listOf(qualifications))
        }
    }

    fun onTitleChange(title: String) {
        _createJobRequest.update {
            it.copy(title = title)
        }
    }

    fun onCategoryChange(category: CategoryResponse) {
        _selectedCategory.value = category
        _createJobRequest.update {
            it.copy(categoryId = category.id!!)
        }
    }

    // Function to fetch job categories from the API
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