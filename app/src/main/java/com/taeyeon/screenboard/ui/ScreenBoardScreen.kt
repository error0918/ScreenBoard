package com.taeyeon.screenboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.system.exitProcess

@Preview(
    device = Devices.DEFAULT,
    showBackground = true
)
@Composable
fun ScreenBoardScreen() {
    GradientBox(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        gradientBoxColor = GradientBoxColor(
            backgroundColor = Color.White,
            layer1Color1 = Color.Red,
            layer1Color2 = Color.Magenta,
            layer2Color1 = Color.Yellow,
            layer2Color2 = Color(0xffffc0cb)
        )
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.TopStart)
                .background(
                    color = Color.Black.copy(alpha = 0.3f),
                    shape = CircleShape
                )
                .padding(horizontal = 8.dp)
        ) {
            CompositionLocalProvider(LocalContentColor provides Color.White) {
                IconButton(
                    onClick = { /* TODO */ }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ChevronLeft,
                        contentDescription = "adsf",
                        tint = Color.White
                    )
                }
                IconButton(
                    onClick = { /* TODO */ }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ChevronRight,
                        contentDescription = "adsf"
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .background(
                    color = Color.Black.copy(alpha = 0.3f),
                    shape = CircleShape
                )
                .padding(horizontal = 8.dp)
        ) {
            CompositionLocalProvider(LocalContentColor provides Color.White) {
                IconButton(
                    onClick = { /* TODO */ }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = "adsf",
                        tint = Color.White
                    )
                }
                IconButton(
                    onClick = { /* TODO */ }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.DoNotTouch,
                        contentDescription = "adsf"
                    )
                }
                IconButton(
                    onClick = { /* TODO */ }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.PowerOff,
                        contentDescription = "adsf"
                    )
                }
                IconButton(
                    onClick = { exitProcess(0) }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "adsf"
                    )
                }
            }
        }

    }
}