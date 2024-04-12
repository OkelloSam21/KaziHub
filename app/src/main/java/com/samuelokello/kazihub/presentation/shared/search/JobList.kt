package com.samuelokello.kazihub.presentation.shared.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun JobList(
    job: JobDTO,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(onClick = { /*TODO*/ }) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = job.profileImage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(16.dp)
                    .height(50.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = job.companyName,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = job.title,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = job.budget +" " + job.location ,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            Column {
                Text(text = "")
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = job.timePosted)
            }
        }
    }
}