package com.samuelokello.kazihub.presentation.shared.search
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.kazihub.R
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn


class SearchViewModel: ViewModel() {
    var searchQuery by mutableStateOf("")
        private set
    var isActive by mutableStateOf(true)
        private set

    var searchHistory by mutableStateOf("")
        private set

    val searchResult: StateFlow<List<JobDTO>> =
        snapshotFlow { searchQuery }
            .combine(jobFlow) { searchQuery, jobs ->
                when {
                    searchQuery.isNotEmpty() -> jobs.filter { job ->
//                        person.firstName.contains(searchQuery, ignoreCase = true)
//                                && person.lastName.contains(searchQuery, ignoreCase = true)
                        job.doesMatchSearchQuery(searchQuery)
                    }

                    else -> jobs
                }
            }.stateIn(
                scope = viewModelScope,
                initialValue = emptyList(),
                started = SharingStarted.WhileSubscribed(5_000)
            )
    fun onSearchQueryChange (newQuery: String){
        searchQuery = newQuery
        if (newQuery.isNotEmpty())
            searchHistory += newQuery
    }

    fun toggleActiveState() {
        isActive = !isActive
    }

}

private val jobFlow = flowOf(
    listOf(
        JobDTO(
            profileImage =R.drawable.icons8_google_48,
            title = "UI/UX Researcher",
            companyName = "Ariana",
            location = "Nairobi",
            position = "UI/UX",
            timePosted = "6h",
            budget = "5k"
        ),
        JobDTO(
            profileImage =R.drawable.icons8_google_48,
            title = "UI/UX Researcher",
            companyName = "Ariana",
            location = "Nairobi",
            position = "UI/UX",
            timePosted = "6h",
            budget = "5k"
        ),
        JobDTO(
            profileImage =R.drawable.icons8_google_48,
            title = "UI/UX Researcher",
            companyName = "Ariana",
            location = "Nairobi",
            position = "UI/UX",
            timePosted = "6h",
            budget = "5k"
        ),
        JobDTO(
            profileImage =R.drawable.icons8_google_48,
            title = "UI/UX Researcher",
            companyName = "Ariana",
            location = "Nairobi",
            position = "UI/UX",
            timePosted = "6h",
            budget = "5k"
        ),
    )
)