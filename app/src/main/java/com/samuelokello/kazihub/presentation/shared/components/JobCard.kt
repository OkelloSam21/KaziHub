package com.samuelokello.kazihub.presentation.shared.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun JobCard(
    @DrawableRes cardIcon: Int,
    @StringRes title: Int,
    @StringRes budget: Int
) {
    ElevatedCard(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .size(width = 200.dp, height = 150.dp)
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
                        Image(
                            painter = painterResource(id = cardIcon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(shape = CircleShape),
                            contentScale = ContentScale.Crop,
                        )
                        Text(text = "Google", style = MaterialTheme.typography.bodyLarge)
                    }
                    Spacer(modifier = Modifier.width(42.dp))
                    Icon(imageVector = Icons.Filled.Star, contentDescription = null)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Spacer(modifier = Modifier.height(32.dp))
                Column(modifier = Modifier.padding(bottom = 8.dp)) {
                    Text(text = stringResource(id = title))

                    Spacer(modifier = Modifier.height(2.dp))
                    Row {
                        Text(text = stringResource(id = budget))
                    }
                }
            }
        }
    }
}