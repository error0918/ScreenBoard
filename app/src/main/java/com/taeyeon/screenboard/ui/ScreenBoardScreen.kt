package com.taeyeon.screenboard.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize

@Preview(
    device = Devices.AUTOMOTIVE_1024p,
    showBackground = true
)
@Composable
fun ScreenBoardScreen() {
    var boxSize by remember { mutableStateOf(IntSize.Zero) }
    val angle by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 36f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 100000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    val xFraction = kotlin.math.sin(angle)
    val yFraction = kotlin.math.cos(angle)
    val biggerSection = if (boxSize.width > boxSize.height) boxSize.width else boxSize.height

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onSizeChanged { intSize -> boxSize = intSize }
            .background(Color.White)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Red,
                        Color.Blue
                    ),
                    start = Offset(
                        x = boxSize.width / 2 + biggerSection * xFraction / 2,
                        y = boxSize.height / 2 + biggerSection * yFraction / 2
                    ),
                    end = Offset(
                        x = boxSize.width / 2 - biggerSection * xFraction / 2,
                        y = boxSize.height / 2 - biggerSection * yFraction / 2
                    )
                ),
                alpha = 0.5f
            )
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Magenta,
                        Color.Green
                    ),
                    start = Offset(
                        x = boxSize.width / 2 + biggerSection * xFraction / 2,
                        y = boxSize.height / 2 - biggerSection * yFraction / 2
                    ),
                    end = Offset(
                        x = boxSize.width / 2 - biggerSection * xFraction / 2,
                        y = boxSize.height / 2 + biggerSection * yFraction / 2
                    )
                ),
                alpha = 0.3f
            )
    ) {
        //
    }
}