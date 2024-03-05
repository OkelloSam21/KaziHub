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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.samuelokello.kazihub.R
import com.samuelokello.kazihub.ui.theme.Verdigris
@Destination
@Composable
fun OnBoardingScreen() {
    OnBoardingContent()
}

@Destination
@Composable
fun OnBoardingContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(.3f),
        ) {
            Image(
                painter = painterResource(id = R.drawable.undraw_software_engineer_re_tnjc),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
//                    .weight(.1f)
//                    .height(200.dp),
            )
        }
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .weight(.2f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 64.dp),
                verticalArrangement = Arrangement.Top,
            ){
                Text(
                    text = "Find a Perfect Job Match",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                )
                Text(
                    text = "Finding your dream job is more easier and faster with Kazihub",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
            ){
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Verdigris,
                        contentColor = MaterialTheme.colors.onPrimary
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
//                    Icon(
//                        imageVector = Icons.Filled.ArrowForward,
//                        contentDescription = null,
//                        modifier = Modifier
//                            .fillMaxWidth()
////                            .padding(start = 4.dp)
//                    )
                    }
                }
            }
        }

    }
}