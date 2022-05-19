package com.quyen.smarthome.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WifiReceiver(private val wifiManager: WifiManager) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
            val devices: ArrayList<String> = arrayListOf()
            val mScanResults: List<ScanResult> = wifiManager.scanResults
            for (result in mScanResults) {
                devices.add(result.SSID)
            }
        }
    }

    companion object {
        const val BUNDLE_WIFI = "BUNDLE_WIFI"
        const val BUNDLE_WIFI_ARG = "BUNDLE_WIFI_ARG"
    }
}
