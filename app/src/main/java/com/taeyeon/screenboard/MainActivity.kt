package com.taeyeon.screenboard

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.taeyeon.screenboard.receiver.BatteryReceiver
import com.taeyeon.screenboard.receiver.MusicReceiver
import com.taeyeon.screenboard.theme.ScreenBoardTheme
import com.taeyeon.screenboard.ui.ScreenBoardScreen
import com.taeyeon.screenboard.ui.viewModel

class MainActivity : ComponentActivity() {
    private val batteryReceiver by lazy { BatteryReceiver(viewModel) }
    private val musicReceiver by lazy { MusicReceiver(viewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            ScreenBoardTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenBoardScreen()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(
            batteryReceiver,
            IntentFilter().apply {
                addAction(Intent.ACTION_POWER_CONNECTED)
                addAction(Intent.ACTION_POWER_DISCONNECTED)
                addAction(Intent.ACTION_BATTERY_CHANGED)
            }
        )
        registerReceiver(
            musicReceiver,
            IntentFilter().apply {
                addAction("com.android.music.metachanged")
                addAction("com.android.music.playstatechanged")
                addAction("com.android.music.playbackcomplete")
                addAction("com.android.music.queuechanged")

                addAction("com.apple.music.playbackstatechange")
                addAction("com.apple.music.metadatachanged")
                addAction("com.apple.music.queuechanged")

                addAction("com.spotify.music.playbackstatechanged")
                addAction("com.spotify.music.metadatachanged")
                addAction("com.spotify.music.queuechanged")
            }
        )
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(batteryReceiver)
        unregisterReceiver(musicReceiver)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            val decorView = window.decorView
            @Suppress("DEPRECATION")
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }
}