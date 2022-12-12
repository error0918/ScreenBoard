package com.taeyeon.screenboard.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize

data class GradientBoxColor(
    val backgroundColor: Color = Color.White,
    val layer1Color1: Color,
    val layer1Color2: Color = layer1Color1.copy(alpha = 0.5f).compositeOver(backgroundColor),
    val layer2Color1: Color,
    val layer2Color2: Color = layer2Color1.copy(alpha = 0.5f).compositeOver(backgroundColor),
)

@Composable
fun GradientBox(
    modifier: Modifier = Modifier,
    gradientBoxColor: GradientBoxColor = GradientBoxColor(
        layer1Color1 = MaterialTheme.colorScheme.primary,
        layer2Color1 = MaterialTheme.colorScheme.secondary
    ),
    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,
    content: @Composable (BoxScope.() -> Unit) = {}
) {
    var boxSize by remember { mutableStateOf(IntSize.Zero) }
    val angle by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = Math.toRadians(360.0).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    val xFraction = kotlin.math.sin(angle)
    val yFraction = kotlin.math.cos(angle)
    val biggerSection = if (boxSize.width > boxSize.height) boxSize.width else boxSize.height

    Box(
        modifier = Modifier
            .onSizeChanged { intSize -> boxSize = intSize }
            .background(gradientBoxColor.backgroundColor)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        gradientBoxColor.layer1Color1,
                        gradientBoxColor.layer1Color2
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
                alpha = 0.75f
            )
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        gradientBoxColor.layer2Color1,
                        gradientBoxColor.layer2Color2
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
                alpha = 0.5f
            )
            .then(modifier),
        contentAlignment = contentAlignment,
        propagateMinConstraints = propagateMinConstraints,
        content = content
    )
}