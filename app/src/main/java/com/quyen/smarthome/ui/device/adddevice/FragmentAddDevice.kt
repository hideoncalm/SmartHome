package com.quyen.smarthome.ui.device.adddevice

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.databinding.FragmentAddDeviceBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FragmentAddDevice : BaseFragment<FragmentAddDeviceBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddDeviceBinding =
        FragmentAddDeviceBinding::inflate

    private var wifiManager: WifiManager? = null
    private var results: List<ScanResult>? = null
    private val arrayList = arrayListOf<String>()
    private var spinnerAdapter : ArrayAdapter<String>? = null
    private var wifidapter : ArrayAdapter<String>? = null

    override fun initViews() {
        spinnerAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, arrayList)
        wifidapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, arrayList)
        binding.spinnerWifi.adapter = spinnerAdapter
        binding.spinnerChooseWifi.adapter = wifidapter
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initData() {

        // scan for all available wifi
        wifiManager = requireContext().getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (!wifiManager!!.isWifiEnabled) {
            Toast.makeText(context, "WiFi is disabled ... We need to enable it", Toast.LENGTH_LONG)
                .show()
            wifiManager!!.isWifiEnabled = true
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context!!.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 0)
        } else {
            scanWifi()
        }
    }

    private fun scanWifi() {
        arrayList.clear()
        context!!.registerReceiver( wifiReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
        wifiManager!!.startScan()
    }


    private var wifiReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            results = wifiManager!!.scanResults
            context.unregisterReceiver(this)
            for (scanResult in results!!) {
                var wifi_ssid = scanResult.SSID
                Timber.d("WIFIScannerActivity: WIFI SSID: $wifi_ssid")
                Timber.d("WIFIScannerActivity: WIFI BSSID: ${scanResult.BSSID}")
                arrayList.add(scanResult.SSID + " - " + scanResult.capabilities)
            }
            spinnerAdapter!!.notifyDataSetChanged()
            wifidapter!!.notifyDataSetChanged()
        }
    }

}
