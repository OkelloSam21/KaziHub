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
        fetchJobCategory() // Fetching data categories when ViewModel is initialized
    }

    // MutableStateFlow to hold the state of the Create data form
    private val _createDataRequest = MutableStateFlow(CreateJobSheetState())
    val createJobState = _createDataRequest.asStateFlow() // Exposing an immutable StateFlow for observing the state

    // MutableStateFlow to hold the list of data categories
    private val _categories = MutableStateFlow<List<CategoryResponse>>(emptyList())
    val categories = _categories.asStateFlow() // Exposing an immutable StateFlow for observing the categories

    private val _selectedCategory = MutableStateFlow<CategoryResponse?>(null)
    val selectedCategory = _selectedCategory.asStateFlow()

    // Function to handle the Create data button click
    fun onCreateJobClick() {
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
    fun onBudgetChange(budget: Int) {
        _createDataRequest.update {
            it.copy(budget = budget)
        }
    }

    fun onDescriptionChange(description: String) {
        _createDataRequest.update {
            it.copy(description = description)
        }
    }

    fun onLocationChange(location: String) {
        _createDataRequest.update {
            it.copy(location = location)
        }
    }

    fun onQualificationsChange(qualifications: String) {
        _createDataRequest.update {
            it.copy(qualifications = listOf(qualifications))
        }
    }

    fun onTitleChange(title: String) {
        _createDataRequest.update {
            it.copy(title = title)
        }
    }

    fun onCategoryChange(category: CategoryResponse) {
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