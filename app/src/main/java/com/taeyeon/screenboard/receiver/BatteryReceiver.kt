package com.taeyeon.screenboard.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.os.Build
import androidx.core.content.getSystemService
import com.taeyeon.screenboard.model.MainViewModel

class BatteryReceiver(private val mainViewModel: MainViewModel) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            when (intent.action) {
                Intent.ACTION_POWER_CONNECTED, Intent.ACTION_POWER_DISCONNECTED, Intent.ACTION_BATTERY_CHANGED -> {
                    val batteryManager = context?.getSystemService<BatteryManager>()
                    val isCharging = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1).let { it == BatteryManager.BATTERY_STATUS_CHARGING || it == BatteryManager.BATTERY_STATUS_FULL }
                    mainViewModel.setBatteryInfo(
                        percent = batteryManager?.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY) ?: mainViewModel.batteryInfo.percent,
                        isCharging = isCharging,
                        chargeTimeRemaining =
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && isCharging) batteryManager?.computeChargeTimeRemaining()
                            else null
                    )
                }
            }
        }
    }
}