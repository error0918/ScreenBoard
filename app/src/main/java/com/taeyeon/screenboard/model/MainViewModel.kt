package com.taeyeon.screenboard.model

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.taeyeon.screenboard.data.BatteryInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel: ViewModel() {
    var time: Calendar by mutableStateOf(Calendar.getInstance())
        private set
    var isTouchProtection by mutableStateOf(false)
        private set
    var batteryInfo by mutableStateOf(
        BatteryInfo(
            percent = 100,
            isCharging = false,
            chargeTimeRemaining = null
        )
    )
        private set

    init {
        CoroutineScope(Dispatchers.Main).launch {
            for (count in 0..Int.MAX_VALUE) {
                delay(100)
                time = Calendar.getInstance()
                // if (count % 600 == 0) {}
            }
        }
    }

    fun changeIsTouchProtection() {
        isTouchProtection = !isTouchProtection
    }

    fun setBatteryInfo(
        percent: Int = batteryInfo.percent,
        isCharging: Boolean = batteryInfo.isCharging,
        chargeTimeRemaining: Long? = batteryInfo.chargeTimeRemaining
    ) {
        batteryInfo = batteryInfo.copy(
            percent = percent,
            isCharging = isCharging,
            chargeTimeRemaining = chargeTimeRemaining
        )
    }
}