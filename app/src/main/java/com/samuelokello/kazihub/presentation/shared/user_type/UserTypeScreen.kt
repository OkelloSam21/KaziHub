package com.samuelokello.kazihub.presentation.shared.user_type

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.R
import com.samuelokello.kazihub.presentation.destinations.SignUpScreenDestination
import com.samuelokello.kazihub.ui.theme.KaziHubTheme
import com.samuelokello.kazihub.utils.UserRole


@Destination
@Composable
fun UserTYpeScreen(navigator: DestinationsNavigator) {
    KaziHubTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            UserTypeContent { userRole -> navigator.navigate(SignUpScreenDestination(userRole = userRole)) }
        }
    }
}

@Composable
fun UserTypeContent(
    onClick: (UserRole) -> Unit
) {
    Column (
        Modifier.fillMaxSize(),
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

        Spacer(modifier = Modifier.height(32.dp))

        Column {
            Text(
                text = "Select your user type",
                style = MaterialTheme.typography.bodyLarge
                .copy(fontSize = 18.sp, fontFamily = FontFamily(Font(R.font.poppins_bold))),
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row (
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            UserTypeCard(
                image = R.drawable.undraw_software_engineer_re_tnjc,
                title = "I am a Job Provider",
                userRole = UserRole.EMPLOYER,
                onClick = {
                    onClick(UserRole.EMPLOYER)
                }
            )
            Spacer(modifier = Modifier.width(32.dp))
            UserTypeCard(
                image = R.drawable.worker,
                title = "I am a Job Seeker",
                userRole = UserRole.WORKER,
                onClick = { onClick(UserRole.WORKER)}
            )
        }
    }
}

@Composable
fun UserTypeCard(
    @DrawableRes image: Int,
    title: String,
    userRole: UserRole,
    onClick: (UserRole) -> Unit
) {
    Card (
        modifier = Modifier
            .padding(start = 8.dp)
            .size(200.dp, 250.dp)
            .clickable { onClick(userRole) }
        .background(MaterialTheme.colorScheme.surface),
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