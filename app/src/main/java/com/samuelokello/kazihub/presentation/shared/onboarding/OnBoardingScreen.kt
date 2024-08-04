package com.samuelokello.kazihub.presentation.shared.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.R
import com.samuelokello.kazihub.presentation.destinations.UserTYpeScreenDestination
import com.samuelokello.kazihub.ui.theme.KaziHubTheme
import com.samuelokello.kazihub.ui.theme.primaryLight


@Destination
@Composable
fun OnBoardingScreen(navigator:DestinationsNavigator) {
    KaziHubTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            OnBoardingContent { navigator.navigate(UserTYpeScreenDestination.route) }
        }
    }

}

@Composable
fun OnBoardingContent(
    onclick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
//            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.Center
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
//                .weight(2f),
        ) {
            Image(
                painter = painterResource(id = R.drawable.undraw_software_engineer_re_tnjc),
                contentDescription = null,
                modifier = Modifier.size(350.dp,350.dp)
            )
        }
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 64.dp),
                verticalArrangement = Arrangement.Center,
            ){
                Text(
                    text = "Find a Perfect data Match",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Finding your dream data is more easier and faster with Kazihub",
                    style = MaterialTheme.typography.titleLarge.copy(color = Color.Gray),
                    modifier = Modifier
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
            ){
                Button(
                    onClick = { onclick() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor =  primaryLight,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(57.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                    Text(text = "Get Started")
                    }
                }
            }
        }

    }
}