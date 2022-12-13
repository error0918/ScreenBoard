package com.taeyeon.screenboard.data

import android.graphics.Bitmap

data class MusicInfo(
    val title: String,
    val artist: String,
    val album: String,
    val albumArt: Bitmap? = null,
    val track: String,
    val player: String,
    val position: Long,
    val duration: Long,
    val isPlaying: Boolean = true
)