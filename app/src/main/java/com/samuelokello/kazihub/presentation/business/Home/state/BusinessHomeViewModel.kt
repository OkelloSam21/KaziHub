package com.samuelokello.kazihub.presentation.business.Home.state

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.kazihub.domain.model.job.create.CreateJobRequest
import com.samuelokello.kazihub.domain.repositpry.JobRepository
import com.samuelokello.kazihub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessHomeViewModel
@Inject constructor(
    private val repository: JobRepository,
    @ApplicationContext private val context: Context,
) : ViewModel() {


    private val _jobs = MutableStateFlow(BusinessHomeUiState())
    val jobs = _jobs.asStateFlow()

    private val _createJobRequest = MutableStateFlow(CreateJobState())
    val createJobRequest = _createJobRequest.asStateFlow()


    fun createJob() {
        viewModelScope.launch {
            val title = _createJobRequest.value.title
            val description = _createJobRequest.value.description
            val budget= _createJobRequest.value.budget
            val location = _createJobRequest.value.location
            val categoryId = _createJobRequest.value.categoryId
            val qualifications = _createJobRequest.value.qualifications

            val request = CreateJobRequest(
                title = title,
                description = description,
                budget = budget,
                categoryId = categoryId,
                location = location,
                qualifications = qualifications.toString()
                )

            when(val response = repository.createJob(request)) {
                is Resource.Success -> {
                    val successMessage = response.message
                    Toast.makeText(context, successMessage?:"Job created successfully", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    val error = response.message
                    Toast.makeText(context, error?:"An error occurred", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun fetchJobs(id: Int) {
        viewModelScope.launch {
            when(val response = repository.fetchJobByBusiness(id)){
                is Resource.Success -> {
                    val job = response.data?.job
                    _jobs.update { it.copy(jobs = job) }
                }
                is Resource.Error -> {
                    val error = response.message
                }
            }
        }

    }

}