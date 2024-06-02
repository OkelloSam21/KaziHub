package com.samuelokello.kazihub.presentation.business.Home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.samuelokello.kazihub.domain.model.job.Job
@Composable
fun JobListItem(job: Job, onJobClick: (Job) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onJobClick(job) },
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = job.title.toString(), style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = job.desc.toString(), style = MaterialTheme.typography.bodyLarge)
        }
    }
}