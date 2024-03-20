package com.samuelokello.kazihub.presentation.shared.home.presentation

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Message
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
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.samuelokello.kazihub.R
import kotlinx.coroutines.launch

//@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen() {
    HomeScreenContent()
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreenContent() {
//    val searchViewModel: SearchViewModel = viewModel()
    val drawerState =
        androidx.compose.material3.rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val onActive by remember { mutableStateOf(false) }
    val searchQuery by remember { mutableStateOf("")}
//    val searchResult by searchViewModel.searchResult.collectAsState()
    var selectedItemIndex by remember { mutableIntStateOf(0) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
                DrawerHeader(
                    userImage = R.drawable.baseline_person_24,
                    userName = "John Doe",
                    userEmail = "johndoe@gmail.com"
                )
                ModalDrawerSheet {
                    Spacer(modifier = Modifier.height(16.dp))
                    navigationItems.forEachIndexed { index, item ->
                        NavigationDrawerItem(
                            label = {
                                Text(text = item.title)
                            },
                            selected = index == selectedItemIndex,
                            onClick = {
//                                            navController.navigate(item.route)
                                selectedItemIndex = index
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) {
                                        item.selectedIcon
                                    } else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            },
                            badge = {
                                item.badgeCount?.let {
                                    Text(text = item.badgeCount.toString())
                                }
                            },
                            modifier = Modifier
                                .padding(NavigationDrawerItemDefaults.ItemPadding),
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                unselectedContainerColor = MaterialTheme.colorScheme.surface,
                            )
                        )
                    }
                }

        }
    ) {
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
                            painter = painterResource(id = R.drawable.baseline_person_24),
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
                        onQueryChange = {  },
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
}

@Composable
fun JobCard(
    @DrawableRes cardIcon: Int,
    @StringRes title: Int,
    @StringRes budget: Int
) {
    ElevatedCard(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
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
                    Column(modifier = Modifier.weight(1f)) {
                        Image(
                            painter = painterResource(id = cardIcon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(shape = CircleShape),
                            contentScale = ContentScale.Crop,
                        )
                        Text(text = "Google", style = MaterialTheme.typography.bodyLarge)
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


data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null
)

sealed class NavigationDrawerItem(
    val route: String,
    val icon: ImageVector,
    val badgeCount: Int? = null
) {
    object Home : NavigationDrawerItem(
        route = "home",
        icon = Icons.Default.Home
    )

    object Messages : NavigationDrawerItem(
        route = "messages",
        icon = Icons.Default.Message
    )

    object Profile : NavigationDrawerItem(
        route = "profile",
        icon = Icons.Default.Person
    )

    object Settings : NavigationDrawerItem(
        route = "settings",
        icon = Icons.Default.Settings
    )
}

val navigationItems = listOf(
    NavigationItem(
        title = "Home",
        selectedIcon = Icons.Default.Home,
        unselectedIcon = Icons.Default.Home
    ),
)

@Composable
fun DrawerHeader(userImage: Int, userName: String, userEmail: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Image(
            painter = painterResource(id = userImage),
            contentDescription = "User Profile Image",
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = userName, style = MaterialTheme.typography.bodyLarge)
        Text(text = userEmail, style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray))
    }
}