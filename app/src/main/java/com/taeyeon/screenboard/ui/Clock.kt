package com.taeyeon.screenboard.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.taeyeon.screenboard.theme.clock
import java.util.Calendar

@Composable
fun TextClock(
    calendar: Calendar,
    modifier: Modifier = Modifier
) {
    Text(
        text = "${calendar.get(Calendar.HOUR_OF_DAY).let { if (it < 10) "0$it" else "$it"}}:${calendar.get(Calendar.MINUTE).let { if (it < 10) "0$it" else "$it"}}:${calendar.get(Calendar.SECOND).let { if (it < 10) "0$it" else "$it"}}.${calendar.get(Calendar.MILLISECOND)}",
        style = clock,
        color = Color.Black,
        modifier = modifier
    )
}