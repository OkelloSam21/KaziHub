package com.samuelokello.kazihub.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.samuelokello.kazihub.R

val poppinsFamily =
    FontFamily(
        Font(R.font.poppins_black),
        Font(R.font.poppins_regular),
        Font(R.font.poppins_bold, weight = FontWeight.Bold),
        Font(R.font.poppins_semibold),
        Font(R.font.poppins_light),
        Font(R.font.poppins_medium)
    )

// Set of Material typography styles to start with
val Typography =
    Typography(
        bodyLarge =
        TextStyle(
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            fontWeight = FontWeight(400),
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),
        titleLarge =
        TextStyle(
            fontFamily = FontFamily(Font(R.font.poppins_medium)),
            fontSize = 22.sp,
            lineHeight = 28.sp,
            fontWeight = FontWeight(500),
//            textAlign = TextAlign.Center
        ),
        labelSmall =
        TextStyle(
            fontSize = 11.sp,
            lineHeight = 16.sp,
            fontFamily = FontFamily(Font(R.font.poppins_medium)),
            fontWeight = FontWeight(500),
            textAlign = TextAlign.Center,
            letterSpacing = 0.5.sp
        ),
        bodyMedium =
        TextStyle(
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            fontWeight = FontWeight(400),
            letterSpacing = 0.25.sp,
            textAlign = TextAlign.Center
        ),
        titleMedium =
        TextStyle(
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontFamily = FontFamily(Font(R.font.poppins_medium)),
            fontWeight = FontWeight(500),
            textAlign = TextAlign.Center,
            letterSpacing = 0.15.sp
        ),
        labelLarge =
        TextStyle(
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = FontFamily(Font(R.font.poppins_medium)),
            fontWeight = FontWeight(500),
            letterSpacing = 0.1.sp
        ),

        // text buttons like LOGIN and NEXT etc
        bodySmall =
        TextStyle(
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = FontFamily(Font(R.font.poppins_semibold)),
            fontWeight = FontWeight(600),
            textAlign = TextAlign.Center,
            letterSpacing = 0.18.sp
        ),
        labelMedium =
        TextStyle(
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontFamily = FontFamily(Font(R.font.poppins_medium)),
            fontWeight = FontWeight(500),
            letterSpacing = 0.5.sp
        ),
        displaySmall =
        TextStyle(
            fontSize = 15.sp,
            lineHeight = 22.sp,
            fontFamily = FontFamily(Font(R.font.poppins_semibold)),
            fontWeight = FontWeight(600),
            letterSpacing = 0.06.sp
        )
    )

//// Set of Material typography styles to start with
//val Typography = Typography(
//    bodyLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Bold,
//        fontSize = 32.sp,
//        lineHeight = 28.sp,
//        letterSpacing = 0.15.sp
//    ),
//    bodyMedium = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Light,
//        fontSize = 18.sp,
//        lineHeight = 28.sp,
//        letterSpacing = 0.sp
//    )
//    /* Other default text styles to override
//    titleLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 22.sp,
//        lineHeight = 28.sp,
//        letterSpacing = 0.sp
//    ),
//    labelSmall = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Medium,
//        fontSize = 11.sp,
//        lineHeight = 16.sp,
//        letterSpacing = 0.5.sp
//    )
//    */
//)