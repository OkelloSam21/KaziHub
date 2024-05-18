package com.samuelokello.kazihub.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.samuelokello.kazihub.R

val poppinsFamily =
    FontFamily(
        Font(R.font.poppins),
    )

// Set of Material typography styles to start with
val Typography =
    Typography(
        bodyLarge =
        TextStyle(
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),
        titleLarge =
        TextStyle(
            fontFamily = poppinsFamily,
            fontSize = 24.sp,
            lineHeight = 28.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        ),
        labelSmall =
        TextStyle(
            fontSize = 11.sp,
            lineHeight = 16.sp,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight(500),
            textAlign = TextAlign.Center,
            letterSpacing = 0.5.sp
        ),
        bodyMedium =
        TextStyle(
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight(400),
            letterSpacing = 0.25.sp,
            textAlign = TextAlign.Center
        ),
        titleMedium =
        TextStyle(
            color = Color.Gray,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Bold,
//            textAlign = TextAlign.Center,
            letterSpacing = 0.15.sp
        ),
        labelLarge =
        TextStyle(
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight(500),
            letterSpacing = 0.1.sp
        ),

        // text buttons like LOGIN and NEXT etc
        bodySmall =
        TextStyle(
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight(600),
            textAlign = TextAlign.Center,
            letterSpacing = 0.18.sp
        ),
        labelMedium =
        TextStyle(
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight(500),
            letterSpacing = 0.5.sp
        ),
        displaySmall =
        TextStyle(
            fontSize = 15.sp,
            lineHeight = 22.sp,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight(600),
            letterSpacing = 0.06.sp
        )
    )
