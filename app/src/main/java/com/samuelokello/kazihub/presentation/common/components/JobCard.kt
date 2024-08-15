package com.samuelokello.kazihub.presentation.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.samuelokello.kazihub.domain.model.job.data

@Composable
fun JobCard(
    job: data,
    onClick: () -> Unit
) {
    ElevatedCard(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .size(width = 200.dp, height = 150.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
//                        AsyncImage(
//                            model = ImageRequest.Builder(LocalContext.current)
//                                .data(data.imageUrl)
//                                .crossfade(true),
//                            placeholder = painterResource(id = R.drawable.icons8_google_48),
//                            error = painterResource(id = R.drawable.icons8_google_48),
//                            contentDescription = null,
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier
//                                .size(40.dp)
//                                .clip(shape = CircleShape),
//                        )
//                        Text(text = homeUiState. ?: "", )
                        Text(text = job.desc ?: "", style = MaterialTheme.typography.bodyLarge)
                    }
                }
                Spacer(modifier = Modifier.width(42.dp))
                Icon(imageVector = Icons.Filled.Star, contentDescription = null)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.height(32.dp))
            Column() {
                Text(text = job.title ?: "")

                Spacer(modifier = Modifier.height(2.dp))
                Row {
                    Text(text = "ksh ${job.budget?: "Not Specified"}")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Location : ${job.business?.location ?: "N/A"} ")
                }
            }
        }
    }
}