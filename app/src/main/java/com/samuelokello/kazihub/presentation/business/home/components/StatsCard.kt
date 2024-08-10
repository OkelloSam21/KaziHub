package com.samuelokello.kazihub.presentation.business.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samuelokello.kazihub.ui.theme.primaryLight

@Composable
fun StatsCard(
    statIcon: @Composable () -> Unit,
    title: String,
    value: String,
) {

    val containerColor: Color = primaryLight
    val contentColor: Color = Color.White
    Box(
        modifier = Modifier
            .size(width = 200.dp, height = 120.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize(),
            colors = CardDefaults.cardColors(
                containerColor = containerColor,
                contentColor = contentColor
            ),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 4.dp
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column (
                modifier = Modifier
                    .padding(horizontal = 26.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                statIcon()
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = title, style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = value, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Preview(showBackground =  true)
@Composable
private fun StatsCardPreview() {
    StatsCard(
        statIcon = { Icons.Default.ShoppingBag},
        title = "Total Expenditure",
        value = "Ksh 100,000",
    )
}