package com.taeyeon.screenboard.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import java.util.concurrent.TimeUnit

data class BatteryInfo(
    var percent: Int,
    var isCharging: Boolean = false,
    var chargeTimeRemaining: Long? = null
) {
    fun getImageVector() =
        if (isCharging) Icons.Rounded.BatteryChargingFull
        else when {
            percent <= 12.5 -> Icons.Rounded.Battery0Bar
            percent <= 25 -> Icons.Rounded.Battery1Bar
            percent <= 37.5 -> Icons.Rounded.Battery2Bar
            percent <= 50 -> Icons.Rounded.Battery3Bar
            percent <= 62.5 -> Icons.Rounded.Battery4Bar
            percent <= 75 -> Icons.Rounded.Battery5Bar
            percent <= 87.5 -> Icons.Rounded.Battery6Bar
            else -> Icons.Rounded.BatteryFull
        }

    fun getChargeTimeRemainingMinutes() =
        if (chargeTimeRemaining != null) TimeUnit.MILLISECONDS.toMinutes(chargeTimeRemaining!!) else null
}
