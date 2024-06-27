package com.samuelokello.kazihub.presentation.business.Home.state

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.kazihub.domain.model.job.Job
import com.samuelokello.kazihub.domain.repositpry.JobRepository
import com.samuelokello.kazihub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessHomeViewModel
@Inject constructor(
    private val repository: JobRepository,
    @ApplicationContext private val context: Context,
) : ViewModel() {

     init {
         fetchJobs(1)
     }

//
    private val _jobs = MutableStateFlow<List<Job?>>(emptyList())
    val jobs = _jobs.asStateFlow()


    private fun fetchJobs(id: Int) {
        viewModelScope.launch {
            when (val response = repository.fetchJobByBusiness(id)) {
                is Resource.Success -> {
                    _jobs.value = response.data?.job?: emptyList()
                }

                is Resource.Error -> {
                    val error = response.message
                }
            }
        }
    }


}
