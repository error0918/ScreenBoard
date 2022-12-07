package com.taeyeon.screenboard.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    device = Devices.AUTOMOTIVE_1024p,
    showBackground = true
)
@Composable
fun ScreenBoardScreen() {
    GradientBox(
        modifier = Modifier.fillMaxSize(),
        gradientBoxColor = GradientBoxColor(
            layer1Color1 = Color.Red,
            layer2Color1 = Color.Yellow
        )
    ) {

    }
}