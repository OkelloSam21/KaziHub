package com.samuelokello.kazihub.presentation.common.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.samuelokello.kazihub.ui.theme.primaryLight

@Composable
fun CustomButton(
    onClick: () -> Unit,
    isEnabled: Boolean = false,
    text: String,
) {
    val containerColor: Color = primaryLight
    val contentColor: Color = Color.White
    val disabledContainerColor: Color = Color.Gray
    val disabledContentColor: Color = Color.Black
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        enabled = isEnabled,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor
        )
    ) {
        Text(text = text)
    }
}