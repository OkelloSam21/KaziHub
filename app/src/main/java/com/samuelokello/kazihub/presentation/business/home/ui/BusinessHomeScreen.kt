
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.presentation.business.home.ui.BusinessHomeScreenContent
import com.samuelokello.kazihub.presentation.business.home.ui.BusinessHomeViewModel
import com.samuelokello.kazihub.presentation.common.components.AppBar
import com.samuelokello.kazihub.presentation.destinations.CreateJobUiDestination
import com.samuelokello.kazihub.presentation.destinations.ProposalUiDestination
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessHomeScreen(
    businessViewModel: BusinessHomeViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val jobs by businessViewModel.jobs.collectAsState()
    val isLoading by businessViewModel.isLoading.collectAsState()
    val uiState by businessViewModel.uiState.collectAsState()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                // Profile section
                ListItem(
                    headlineContent = { Text("John Doe") },
                    supportingContent = { Text("john.doe@example.com") },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Person,
                            contentDescription = "Profile"
                        )
                    }
                )
                HorizontalDivider()
                // Navigation items
                NavigationDrawerItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                    label = { Text("Home") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Filled.Description, contentDescription = null) },
                    label = { Text("Proposals") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navigator.navigate(ProposalUiDestination(1))
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                AppBar(
                    onNavigationIconClick = {
                        scope.launch { drawerState.open() }
                    }
                )
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when {
                    isLoading -> CircularProgressIndicator()
                    else -> BusinessHomeScreenContent(
                        jobs = jobs,
                        navigateToCreateJob = { navigator.navigate(CreateJobUiDestination) },
                        onBusinessUiEvent = businessViewModel::businessHomeEvent
                    )
                }
            }
        }
    }
}