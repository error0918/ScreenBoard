@file:OptIn(ExperimentalAnimationApi::class)
@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.taeyeon.screenboard.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.taeyeon.screenboard.R
import com.taeyeon.screenboard.model.MainViewModel
import java.util.*
import kotlin.system.exitProcess


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
        Controller()

        val viewModel = remember { MainViewModel() }
        val calendar by viewModel.time  .observeAsState(Calendar.getInstance())
        TextClock(
            calendar = calendar,
            modifier = Modifier.align(Alignment.Center)
        )

    }
}


@Composable
fun Controller(
    modifier: Modifier = Modifier
) {
    var isHidden by rememberSaveable { mutableStateOf(false) }
    AnimatedContent(
        targetState = isHidden,
        //transitionSpec = ,
        modifier = modifier
    ) {
        if (it) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconButton(
                    onClick = { isHidden = false },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .background(
                            color = Color.Black.copy(alpha = 0.3f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowDropDown,
                        contentDescription = stringResource(id = R.string.floatingbutton_move_left),
                        tint = Color.White
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
                            onClick = { /* TODO */ }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ChevronLeft,
                                contentDescription = stringResource(id = R.string.floatingbutton_move_left)
                            )
                        }
                        IconButton(
                            onClick = { /* TODO */ }
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
                            onClick = { /* TODO */ }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.MoreVert,
                                contentDescription = stringResource(id = R.string.floatingbutton_morevert)
                            )
                        }
                        IconButton(
                            onClick = { isHidden = true }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.KeyboardArrowUp,
                                contentDescription = stringResource(id = R.string.floatingbutton_hide)
                            )
                        }
                        IconButton(
                            onClick = { /* TODO */ }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.DoNotTouch,
                                contentDescription = stringResource(id = R.string.floatingbutton_touch_protection)
                            )
                        }
                        IconButton(
                            onClick = { /* TODO */ }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.PowerOff,
                                contentDescription = stringResource(id = R.string.floatingbutton_poweroff)
                            )
                        }
                        IconButton(
                            onClick = { exitProcess(0) }
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