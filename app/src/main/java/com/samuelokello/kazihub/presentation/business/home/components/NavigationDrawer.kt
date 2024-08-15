package com.samuelokello.kazihub.presentation.business.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.R

@Composable
fun NavigationDrawer(
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit,
    navigator: DestinationsNavigator
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Profile Section
        ProfileSection()
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Navigation Items
        NavigationItem(
            icon = Icons.Default.Home,
            label = "Home",
            onClick = { onItemClick("Home") }
        )
        NavigationItem(
            icon = Icons.Default.List,
            label = "Proposals",
            onClick = { 
//                navigator.navigate(ProposalsScreenDestination)
                onItemClick("Proposals")
            }
        )
        // Add more navigation items as needed
    }
}

@Composable
fun ProfileSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.male_user_2),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(100.dp)
                .padding(8.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = "John Doe",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "john.doe@example.com",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun NavigationItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(32.dp))
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
    }
}