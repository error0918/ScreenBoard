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
        text = "${getDigitNumber(calendar.get(Calendar.HOUR_OF_DAY), 2)}:${getDigitNumber(calendar.get(Calendar.MINUTE), 2)}:${getDigitNumber(calendar.get(Calendar.SECOND), 2)}.${calendar.get(Calendar.MILLISECOND) / 100}",
        style = clock,
        color = Color.Black,
        modifier = modifier
    )
}


fun getDigitNumber(number: Int, digits: Int): String {
    return if (digits > 0) {
        if (number.toString().length >= digits) {
            number.toString().substring(0, digits)
        } else {
            "0".repeat(digits - number.toString().length) + number
        }
    } else ""
}