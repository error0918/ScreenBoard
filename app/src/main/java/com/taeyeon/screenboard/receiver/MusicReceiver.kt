package com.taeyeon.screenboard.receiver

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.AudioManager
import android.util.Log
import androidx.core.content.getSystemService
import com.taeyeon.screenboard.data.MusicInfo

class MusicReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("asdf", "asdf")
        if (context != null && intent != null) {
android.widget.Toast.makeText(context, "asdf", android.widget.Toast.LENGTH_SHORT).show()
            intent.extras?.let { extras ->
                val title = "extras.getString"

                val artist = extras.getString("artist") ?: "Unknown"
                val album = extras.getString("") ?: "Unknown"
                val albumArt = extras.getParcelable(Notification.EXTRA_LARGE_ICON_BIG) as Bitmap?
                val track = extras.getString("track") ?: "Unknown"
                val player = extras.getString("player") ?: "Unknown"
                val position = extras.getLong("position")
                val duration = extras.get("duration").let { durationObject ->
                    if (durationObject == null) -1 else when (durationObject) {
                        is Long -> durationObject
                        is Double -> durationObject.toLong()
                        is Float -> durationObject.toLong()
                        is Int -> durationObject.toLong() * 1000
                        is String -> java.lang.Double.valueOf(durationObject).toLong()
                        else -> -1
                    }
                }
                val isPlaying = context.getSystemService<AudioManager>()?.isMusicActive ?: false

                val musicInfo = MusicInfo(
                    title = title,
                    artist = artist,
                    album = album,
                    albumArt = albumArt,
                    track = track,
                    player = player,
                    position = position,
                    duration = duration,
                    isPlaying = isPlaying
                )

                Log.e("ASDF", musicInfo.toString())




            }
        }
    }
}