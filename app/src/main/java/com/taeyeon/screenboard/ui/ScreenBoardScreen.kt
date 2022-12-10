@file:OptIn(
    ExperimentalAnimationApi::class,
    ExperimentalMaterialApi::class, ExperimentalFoundationApi::class
)
@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.taeyeon.screenboard.ui

import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.content.getSystemService
import com.taeyeon.screenboard.R
import com.taeyeon.screenboard.model.MainViewModel
import java.util.*
import kotlin.system.exitProcess


val viewModel by lazy { MainViewModel() }

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
        Controller(
            modifier = Modifier.align(Alignment.TopCenter)
        )

        val calendar by viewModel.time.observeAsState(Calendar.getInstance())
        TextClock(
            calendar = calendar,
            modifier = Modifier.align(Alignment.Center)
        )

        InformationBar(
            modifier = Modifier.align(Alignment.BottomCenter)
        )

    }
}


@Composable
fun Controller(
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = viewModel.isTouchProtection.observeAsState(initial = false).value,
        transitionSpec = {
            scaleIn() + slideIn() { fullSize ->
                IntOffset(0, -fullSize.height)
            } with scaleOut() + slideOut() { fullSize ->
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
                        viewModel.changeIsTouchProtection()
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
                                contentDescription = stringResource(id = R.string.floatingbutton_move_left)
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
                                contentDescription = stringResource(id = R.string.floatingbutton_move_right)
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
                                contentDescription = stringResource(id = R.string.floatingbutton_morevert)
                            )
                        }
                        IconButton(
                            onClick = {
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                viewModel.changeIsTouchProtection()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.DoNotTouch,
                                contentDescription = stringResource(id = R.string.floatingbutton_touch_protection)
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
                                contentDescription = stringResource(id = R.string.floatingbutton_poweroff)
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
                                contentDescription = stringResource(id = R.string.floatingbutton_close)
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
    val batteryManager = LocalContext.current.getSystemService<BatteryManager>()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        batteryManager?.let {
            val batteryPercent = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
            val isCharging = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { intentFilter ->
                LocalContext.current.registerReceiver(null, intentFilter)
            }?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) == BatteryManager.BATTERY_STATUS_CHARGING

            Icon(
                imageVector =
                if (isCharging) Icons.Rounded.BatteryChargingFull
                else when {
                    batteryPercent <= 12.5 -> Icons.Rounded.Battery0Bar
                    batteryPercent <= 25 -> Icons.Rounded.Battery1Bar
                    batteryPercent <= 37.5 -> Icons.Rounded.Battery2Bar
                    batteryPercent <= 50 -> Icons.Rounded.Battery3Bar
                    batteryPercent <= 62.5 -> Icons.Rounded.Battery4Bar
                    batteryPercent <= 75 -> Icons.Rounded.Battery5Bar
                    batteryPercent <= 87.5 -> Icons.Rounded.Battery6Bar
                    else -> Icons.Rounded.BatteryFull
                },
                contentDescription = stringResource(id = R.string.imformationbar_battery_info_image)
            )
            Text(
                text = "${batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)}% " +
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && isCharging)
                            batteryManager.computeChargeTimeRemaining().let {
                                if (it == -1L) ""
                                else "(${
                                    it
                                })"
                            }
                        else "",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}