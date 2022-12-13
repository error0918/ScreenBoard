@file:OptIn(
    ExperimentalAnimationApi::class,
    ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class
)
@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.taeyeon.screenboard.ui

import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.taeyeon.screenboard.R
import com.taeyeon.screenboard.model.MainViewModel
import kotlin.system.exitProcess


val viewModel by lazy { MainViewModel() }

@Composable
fun ScreenBoardScreen() {
    GradientBox(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        gradientBoxColor = GradientBoxColor(
            backgroundColor = Color.Black,
            layer1Color1 = Color.Blue,
            layer1Color2 = Color.Red,
            layer2Color1 = Color(0xFFE66A0B),
            layer2Color2 = Color(0xFFFF00E4)
        )
    ) {
        ControlBar(
            modifier = Modifier.align(Alignment.TopCenter)
        )

        TextClock(
            calendar = viewModel.time,
            modifier = Modifier.align(Alignment.Center)
        )

        InformationBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .alpha(0.6f)
        )

    }
}


@Composable
fun ControlBar(
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = viewModel.isTouchProtection,
        transitionSpec = {
            scaleIn() + slideIn { fullSize ->
                IntOffset(0, -fullSize.height)
            } with scaleOut() + slideOut { fullSize ->
                IntOffset(0, -fullSize.height)
            }
        },
        modifier = modifier
    ) {
        val hapticFeedback = LocalHapticFeedback.current

        if (it)  {
            val swipeableState = rememberSwipeableState(
                initialValue = 0,
                confirmStateChange = { value ->
                    if (value == 1) {
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        viewModel.isTouchProtection = !viewModel.isTouchProtection
                    }
                    true
                }
            )

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .width(
                            240.dp
                                    + with(LocalDensity.current) {
                                if (swipeableState.offset.value < -0) -swipeableState.offset.value.toDp() * 2
                                else if (swipeableState.offset.value.toDp() + 48.dp > 240.dp) (swipeableState.offset.value.toDp() + 48.dp - 240.dp) * 2
                                else 0.dp
                            }
                        )
                        .height(48.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.3f),
                            shape = CircleShape
                        )
                ) {
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                vertical = 12.dp,
                                horizontal = 12.dp
                            ),
                        contentDescription = stringResource(id = R.string.clear_touch_protection_arrow)
                    ) {
                        val dp12 = 12.dp.toPx()
                        val padding = (size.width - 7 * dp12) / 8

                        for (i in 0..6) {
                            drawPath(
                                path = Path().apply {
                                    moveTo((dp12 + padding) * i + padding, 0f)
                                    lineTo((dp12 + padding) * i + padding + dp12, size.height / 2)
                                    lineTo((dp12 + padding) * i + padding, size.height)
                                },
                                color = Color.Black,
                                alpha = 0.2f,
                                style = Stroke(6.dp.toPx(), cap = StrokeCap.Round)
                            )
                        }

                    }
                    Icon(
                        imageVector = Icons.Rounded.DoNotTouch,
                        contentDescription = stringResource(id = R.string.clear_touch_protection),
                        tint = Color.White,
                        modifier = Modifier
                            .padding(
                                horizontal = with(LocalDensity.current) {
                                    if (swipeableState.offset.value < -0) -swipeableState.offset.value.toDp()
                                    else if (swipeableState.offset.value.toDp() + 48.dp > 240.dp) swipeableState.offset.value.toDp() + 48.dp - 240.dp
                                    else 0.dp
                                }
                            )
                            .size(48.dp)
                            .align(Alignment.CenterStart)
                            .offset(with(LocalDensity.current) { swipeableState.offset.value.toDp() })
                            .background(
                                color = Color.Black,
                                shape = CircleShape
                            )
                            .padding(12.dp)
                            .swipeable(
                                state = swipeableState,
                                anchors = with(LocalDensity.current) {
                                    mapOf(
                                        0f to 0,
                                        240.dp.toPx() - 48.dp.toPx() to 1
                                    )
                                },
                                orientation = Orientation.Horizontal,
                                thresholds = { _, _ -> FractionalThreshold(1f) },
                                velocityThreshold = 100.dp
                            )
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .background(
                            color = Color.Black.copy(alpha = 0.3f),
                            shape = CircleShape
                        )
                        .padding(horizontal = 4.dp)
                ) {
                    CompositionLocalProvider(LocalContentColor provides Color.White) {
                        IconButton(
                            onClick = {
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                /* TODO */
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ChevronLeft,
                                contentDescription = stringResource(id = R.string.control_bar_floatingbutton_move_left)
                            )
                        }
                        IconButton(
                            onClick = {
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                /* TODO */
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ChevronRight,
                                contentDescription = stringResource(id = R.string.control_bar_floatingbutton_move_right)
                            )
                        }
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .background(
                            color = Color.Black.copy(alpha = 0.3f),
                            shape = CircleShape
                        )
                        .padding(horizontal = 4.dp)
                ) {
                    CompositionLocalProvider(LocalContentColor provides Color.White) {
                        IconButton(
                            onClick = {
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                /* TODO */
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.MoreVert,
                                contentDescription = stringResource(id = R.string.control_bar_floatingbutton_morevert)
                            )
                        }
                        IconButton(
                            onClick = {
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                viewModel.isTouchProtection = !viewModel.isTouchProtection
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.DoNotTouch,
                                contentDescription = stringResource(id = R.string.control_bar_floatingbutton_touch_protection)
                            )
                        }
                        IconButton(
                            onClick = {
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                /* TODO */
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.PowerOff,
                                contentDescription = stringResource(id = R.string.control_bar_floatingbutton_poweroff)
                            )
                        }
                        IconButton(
                            onClick = {
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                exitProcess(0)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = stringResource(id = R.string.control_bar_floatingbutton_close)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun InformationBar(
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(LocalContentColor provides Color.White) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(horizontal = 4.dp)
                .then(modifier),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .widthIn(min = 32.dp + 8.dp)
            ) {
                IconButton(
                    onClick = { /* TODO */ },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Notifications,
                        contentDescription = stringResource(id = R.string.information_bar_notifications)
                    )
                }
            }

            IconButton(
                onClick = { viewModel.isBrightnessBarVisible = !viewModel.isBrightnessBarVisible },
                enabled = !viewModel.isTouchProtection,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Brightness7,
                    contentDescription = stringResource(id = R.string.information_bar_brightness)
                )
            }
            AnimatedVisibility(visible = viewModel.isBrightnessBarVisible && !viewModel.isTouchProtection) {
                var value by remember { mutableStateOf(0.5f) }
                BoxSlider(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .width(80.dp)
                        .height(24.dp),
                    value = value,
                    onValueChange = { value = it }
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = { viewModel.isVolumeBarVisible = !viewModel.isVolumeBarVisible },
                enabled = !viewModel.isTouchProtection,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.VolumeUp,
                    contentDescription = stringResource(id = R.string.information_bar_volume)
                )
            }
            AnimatedVisibility(visible = viewModel.isVolumeBarVisible && !viewModel.isTouchProtection) {
                var value by remember { mutableStateOf(0.5f) }
                BoxSlider(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .width(80.dp)
                        .height(24.dp),
                    value = value,
                    onValueChange = { value = it }
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = { viewModel.isBatteryVisible = !viewModel.isBatteryVisible },
                enabled = !viewModel.isTouchProtection,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = viewModel.batteryInfo.getImageVector(),
                    contentDescription = stringResource(id = R.string.information_bar_battery_info_image)
                )
            }
            AnimatedVisibility(visible = viewModel.isBatteryVisible) {
                Text(
                    text = "${viewModel.batteryInfo.percent}%" +
                            viewModel.batteryInfo.getChargeTimeRemainingMinutes()
                                .let { if (it != null && it != 0L) " (${it}m)" else "" },
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .animateContentSize()
                        .padding(start = 4.dp)
                )
            }

        }
    }
}