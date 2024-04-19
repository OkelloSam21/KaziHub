package com.samuelokello.kazihub.presentation.worker

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterAlt
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.samuelokello.kazihub.R
import com.samuelokello.kazihub.presentation.shared.components.AppBar
import com.samuelokello.kazihub.presentation.shared.components.JobCard
import kotlinx.coroutines.launch

/**
 * Worker Home Screen
 * A composable function that displays the worker home screen
 * It contains a search bar, popular jobs, and recent posts
 * */

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WorkerHomeScreen() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val onActive by remember { mutableStateOf(false) }
    val searchQuery by remember { mutableStateOf("") }
    var selectedItemIndex by remember { mutableIntStateOf(0) }

//    ModalNavigationDrawer(
//        drawerState = drawerState,
//        drawerContent = {
//            ModalDrawerSheet {
//                Spacer(modifier = Modifier.height(16.dp))
//                navigationItems.forEachIndexed { index, item ->
//                    NavigationDrawerItem(
//                        label = {
//                            Text(text = item.title)
//                        },
//                        selected = index == selectedItemIndex,
//                        onClick = {
////                                            navController.navigate(item.route)
//                            selectedItemIndex = index
//                            scope.launch {
//                                drawerState.close()
//                            }
//                        },
//                        icon = {
//                            Icon(
//                                imageVector = if (index == selectedItemIndex) {
//                                    item.selectedIcon
//                                } else item.unselectedIcon,
//                                contentDescription = item.title
//                            )
//                        },
//                        badge = {
//                            item.badgeCount?.let {
//                                Text(text = item.badgeCount.toString())
//                            }
//                        },
//                        modifier = Modifier
//                            .padding(NavigationDrawerItemDefaults.ItemPadding),
//                        colors = NavigationDrawerItemDefaults.colors(
//                            selectedContainerColor = MaterialTheme.colorScheme.primary,
//                            unselectedContainerColor = MaterialTheme.colorScheme.surface,
//                        )
//                    )
//                }
//            }
//        }
//    ) {
        Scaffold(
            topBar = {
                AppBar {
                    scope.launch {
                        drawerState.open()
                    }
                }
            },
            modifier = Modifier.padding(bottom = 32.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 32.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),

                ) {
                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    SearchBar(
                        query = searchQuery,
                        onQueryChange = { },
                        onSearch = {},
                        active = onActive,
                        onActiveChange = {},
                        colors = SearchBarDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.onSecondary,
                        ),
                        modifier = Modifier
                            .width(210.dp)
                            .height(40.dp)
                            .padding(start = 8.dp)
                    ) {
                        if (searchQuery.isNotEmpty()) {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                contentPadding = PaddingValues(20.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
//                                items(searchResult) { job ->
////                                    JobList(job)
//                                    HorizontalDivider(
//                                        Modifier.fillMaxWidth(),
//                                        thickness = 0.5.dp,
//                                        color = Color.Gray
//                                    )
//                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        imageVector = Icons.Rounded.FilterAlt,
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .padding(end = 8.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column {
                    Column {
                        Row {
                            Text(text = "Popular Jobs", modifier = Modifier.weight(1f))

                            Spacer(modifier = Modifier.width(32.dp))

                            TextButton(
                                onClick = { /*TODO*/ }) {
                                Text(text = "View All")
                            }
                        }

                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth(),
//                            .height(100.dp),
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            items(jobs) {
                                JobCard(it.icon, it.title, it.budget)
                                Spacer(modifier = Modifier.width(16.dp))
                            }

                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    Column {
                        Row(horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Recent Posts", modifier = Modifier.weight(1f))

                            Spacer(modifier = Modifier.width(64.dp))

                            TextButton(
                                onClick = { /*TODO*/ }) {
                                Text(text = "View All", style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        LazyColumn {
                            items(jobs) {
                                RecentPost()
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
//}

/**
 * Recent Post
 * A composable function that displays a recent post
 * */
@Composable
fun RecentPost() {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        content = {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icons8_google_48),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )

                    Column {
                        Text(text = "UI/UX")
                        Text(text = "full time")
                    }

                    Text(text = "ksh 1400")
                }
            }
        }
    )
}

/**
 * Job List
 * A composable function that displays a job list
 * */
data class JobData(
    val icon: Int,
    val title: Int,
    val budget: Int,
    val time: String? = null
)

/**
 * Jobs
 * A list of jobs
 * */

val jobs = listOf(
    JobData(R.drawable.icons8_google_48, R.string.title_1, R.string.budget_1),
    JobData(R.drawable.icons8_google_48, R.string.title_2, R.string.budget_2),
    JobData(R.drawable.icons8_google_48, R.string.title_3, R.string.budget_3),
    JobData(R.drawable.icons8_google_48, R.string.title_1, R.string.budget_1),
    JobData(R.drawable.icons8_google_48, R.string.title_2, R.string.budget_2),
    JobData(R.drawable.icons8_google_48, R.string.title_3, R.string.budget_3),
)