package com.taeyeon.screenboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity


@Composable
fun BoxSlider(
    modifier: Modifier = Modifier,
    value: Float,
    onValueChanged: (value :Float) -> Unit
) {
    var sliderWidth by remember { mutableStateOf(0) }
    Surface(
        color = Color.DarkGray,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .onSizeChanged { intSize -> sliderWidth = intSize.width }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        onValueChanged(
                            value.plus(dragAmount.x / sliderWidth).let {
                                if (it in 0f..1f) it
                                else if (it > 1f) 1f
                                else 0f
                            }
                        )
                    }
                )
            }
            .then(modifier)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .width(with(LocalDensity.current) { sliderWidth.toDp() * value })
                    .fillMaxHeight()
                    .align(Alignment.CenterStart)
                    .background(Color.White)
            )
        }
    }
}