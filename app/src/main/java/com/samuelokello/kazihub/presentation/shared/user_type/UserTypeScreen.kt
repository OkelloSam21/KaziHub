package com.samuelokello.kazihub.presentation.shared.user_type

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
            UserTypeContent { userType -> navigator.navigate(SignUpScreenDestination(userType)) }
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
        Column {
            Image(
                painter = painterResource(id = R.drawable.kazi_hub_logo),
                contentDescription = "Kazi Hub Logo",
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column {
            Text(
                text = "Select your user type",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row (
            Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column {
                UserTypeCard(
                    image = R.drawable.undraw_software_engineer_re_tnjc,
                    title = "I am a job Provider",
                    userRole = UserRole.BUSINESS,
                    onClick = {
                        onClick(UserRole.BUSINESS)
                    }
                )
                Text(
                    text = "Business",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(horizontal = 64.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                UserTypeCard(
                    image = R.drawable.worker,
                    title = "I am a job Seeker",
                    userRole = UserRole.WORKER,
                    onClick = { onClick(UserRole.WORKER)}
                )
                Text(
                    text = "Worker",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(horizontal = 64.dp)
                )
            }


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
        modifier =
        Modifier.width(170.dp)
            .height(180.dp)
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .shadow(elevation = 12.dp, spotColor = Color(0x26BFBFD9), ambientColor = Color(0x26BFBFD9))
            .shadow(elevation = 4.dp, spotColor = Color(0x1ABFBFD9), ambientColor = Color(0x1ABFBFD9))
            .border(width = 0.5.dp, color = MaterialTheme.colorScheme.onSurfaceVariant, shape = RoundedCornerShape(size = 16.dp))
    ){
        Column (
            modifier = Modifier.fillMaxWidth().clickable(onClick = { onClick(userRole) }),
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
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
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}