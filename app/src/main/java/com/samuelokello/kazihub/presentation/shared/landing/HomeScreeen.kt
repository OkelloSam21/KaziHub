package com.samuelokello.kazihub.presentation.shared.landing

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.FilterAlt
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.samuelokello.kazihub.R
import kotlinx.coroutines.launch

@Destination
@Composable
fun HomeScreen() {
    HomeScreenContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreenContent() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedItem = remember { mutableIntStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    val onActive by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                title = { /*TODO*/ },
                navigationIcon = {
                    IconButton(onClick = {
                        if (drawerState.isClosed) {
                            scope.launch { drawerState.open() }
                        } else {
                            scope.launch { drawerState.close() }
                        }
                    }) {
                        Icon(
                            Icons.Rounded.Menu,
                            contentDescription = "Drawer",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(32.dp)
                                .padding(0.dp)
                                .clip(RoundedCornerShape(0.dp))
//                                .background(MaterialTheme.colorScheme.primary)
                        )
                    }
                },
                actions = {
                    Image(
                        painter = painterResource(id = R.drawable.icons8_google_48),
                        contentDescription = null,
                        Modifier
                            .size(60.dp)
                            .clip(shape = CircleShape)
                            .padding(end = 32.dp),
                    )
                }
            )
        },
        modifier = Modifier.padding(bottom = 32.dp),

        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.onSecondary,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomEnd = 0.dp,
                            bottomStart = 0.dp
                        )
                    ),
                content = {
                    NavigationBarItem(
                        icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = null) },
                        label = { Text("Home") },
                        selected = selectedItem.intValue == 0,
                        onClick = { selectedItem.intValue = 0 },
                    )
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Message,
                                contentDescription = null
                            )
                        },
                        label = { Text("Message") },
                        selected = selectedItem.intValue == 1,
                        onClick = { selectedItem.intValue = 1 },
                    )
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = null
                            )
                        },
                        label = { Text("Profile") },
                        selected = selectedItem.intValue == 2,
                        onClick = { selectedItem.intValue = 2 },
                    )
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = null
                            )
                        },
                        label = { Text("Settings") },
                        selected = selectedItem.intValue == 3,
                        onClick = { selectedItem.intValue = 3 },
                    )

                }
            )
        }
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
                    onQueryChange = { searchQuery = it },
                    onSearch = {},
                    active = onActive,
                    onActiveChange = {},
                    colors = SearchBarDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.onSecondary,
                    ),
                    modifier = Modifier
                        .width(120.dp)
                        .height(40.dp)
//                        .weight(.3f)
                        .padding(start = 8.dp)
                ) {}
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
                    Row (horizontalArrangement = Arrangement.SpaceBetween){
                        Text(text = "Recent Posts", modifier = Modifier.weight(1f))

                        Spacer(modifier = Modifier.width(64.dp))

                        TextButton(
                            onClick = { /*TODO*/ }) {
                            Text(text = "View All", style = MaterialTheme.typography.bodySmall)
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

@Composable
fun JobCard(
    @DrawableRes cardIcon: Int,
    @StringRes title: Int,
    @StringRes budget: Int
) {
    ElevatedCard(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation( defaultElevation = 4.dp),
        modifier = Modifier
            .size(width = 200.dp, height = 150.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column (modifier = Modifier.weight(1f)){
                        Image(
                            painter = painterResource(id = cardIcon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(shape = CircleShape),
                            contentScale = ContentScale.Crop,
                        )
                        Text(text = "Google", style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(modifier = Modifier.width(42.dp))
                    Icon(imageVector = Icons.Filled.Star, contentDescription = null)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Spacer(modifier = Modifier.height(32.dp))
                Column(modifier = Modifier.padding(bottom = 8.dp)) {
                    Text(text = stringResource(id = title))

                    Spacer(modifier = Modifier.height(2.dp))
                    Row {
                        Text(text = stringResource(id = budget))
                    }
                }
            }
        }
    }
}


@Composable
fun RecentPost() {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
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
}


data class JobData(
    val icon: Int,
    val title: Int,
    val budget: Int,
    val time: String? = null
)

val jobs = listOf(
    JobData(R.drawable.icons8_google_48, R.string.title_1, R.string.budget_1),
    JobData(R.drawable.icons8_google_48, R.string.title_2, R.string.budget_2),
    JobData(R.drawable.icons8_google_48, R.string.title_3, R.string.budget_3),
    JobData(R.drawable.icons8_google_48, R.string.title_1, R.string.budget_1),
    JobData(R.drawable.icons8_google_48, R.string.title_2, R.string.budget_2),
    JobData(R.drawable.icons8_google_48, R.string.title_3, R.string.budget_3),
)

//Column {
//    jobs.forEach { job ->
//        JobCard(job.icon, job.title, job.budget)
//    }
//}
