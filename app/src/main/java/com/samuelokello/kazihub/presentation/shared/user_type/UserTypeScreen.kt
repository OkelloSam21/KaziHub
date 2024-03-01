package com.samuelokello.kazihub.presentation.shared.user_type

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.R
import com.samuelokello.kazihub.presentation.destinations.HomeScreenDestination

@RootNavGraph(start = true)
@Destination
@Composable
fun UserTYpeScreen(navigator: DestinationsNavigator) {
    UserTypeContent(
        navigator = navigator
    )
}

@Composable
fun UserTypeContent(
    navigator: DestinationsNavigator
) {
    Column (
        Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column (
            verticalArrangement = Arrangement.Top,
        ){
            Image(
                painter = painterResource(id = R.drawable.kazi_hub_logo),
                contentDescription = "Kazi Hub Logo"
            )
        }
        Column {
            Text(
                text = "Select your user type",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp, fontWeight = FontWeight.ExtraBold),
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row (
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            UserTypeCard(
                image = R.drawable.employer,
                title = "I am a Job Provider",
                onClick = {
                    navigator.navigate(HomeScreenDestination.route)
                }
            )
            Spacer(modifier = Modifier.width(32.dp))
            UserTypeCard(
                image = R.drawable.worker,
                title = "I am a Job Seeker",
                onClick = {
                    navigator.navigate(HomeScreenDestination.route)
                }
            )
        }
    }
}

@Composable
fun UserTypeCard(
    @DrawableRes image: Int,
    title: String,
    onClick: () -> Unit
) {
    ElevatedCard (
        modifier = Modifier
            .padding(start = 8.dp)
            .size(200.dp, 250.dp)
            .clickable { onClick() },

    ){
        Column (
            verticalArrangement = Arrangement.Center,
        ){
            Image(
                painter = painterResource(id = image),
                contentDescription = title,
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}