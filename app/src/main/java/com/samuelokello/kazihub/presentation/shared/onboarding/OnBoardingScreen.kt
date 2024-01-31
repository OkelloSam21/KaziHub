package com.samuelokello.kazihub.presentation.shared.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.samuelokello.kazihub.R
import com.samuelokello.kazihub.ui.theme.Verdigris

@Composable
fun GettingStartedScreen() {
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
                    .padding( bottom = 64.dp),
                verticalArrangement = Arrangement.Top,
            ){
                Text(
                    text = "Find a Perfect Job Match",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                )
                Text(
                    text = "Finding your dream job is more easier and faster with Kazihub",
                    style = MaterialTheme.typography.bodyMedium,
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
                        containerColor = Verdigris,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(57.dp)
                ) {
//                Row{
                    Text(text = "Get Started")
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp)
                    )
//                }
                }
            }
        }

    }
}