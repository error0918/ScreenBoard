package com.taeyeon.screenboard.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.taeyeon.screenboard.R

val gmarketSans = FontFamily(
    Font(R.font.gmarketsans_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.gmarketsans_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.gmarketsans_light, FontWeight.Light, FontStyle.Normal)
)

val clock = TextStyle(
    fontFamily = gmarketSans,
    fontWeight = FontWeight.Bold,
    fontSize = 100.sp,
    lineHeight = 128.sp,
    letterSpacing = 4.sp
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)