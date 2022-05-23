package com.quyen.smarthome.ui.device.adddevice

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.databinding.FragmentAddDeviceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentAddDevice : BaseFragment<FragmentAddDeviceBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddDeviceBinding =
        FragmentAddDeviceBinding::inflate

    lateinit var wifiManager: WifiManager
    private var results: ArrayList<ScanResult> = arrayListOf()

    private var wifiReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            results = wifiManager.scanResults as ArrayList<ScanResult>
            Toast.makeText(context, "OnReceiver call", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wifiManager = context?.getSystemService(Context.WIFI_SERVICE) as WifiManager
        startScanning()
    }

    override fun initViews() {
        binding.buttonContinue.setOnClickListener {
            stopScanning()
            val intentChoseWifi = Intent(WifiManager.ACTION_PICK_WIFI_NETWORK)
            activity?.startActivityForResult(intentChoseWifi, REQUEST_CODE)
        }
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initData() {
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun startScanning() {
        context?.registerReceiver(
            wifiReceiver,
            IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        )
        wifiManager.startScan()
    }

    private fun stopScanning() {
        context?.unregisterReceiver(wifiReceiver)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE)
        {
            if(resultCode == Activity.RESULT_OK)
            {

            }
        }
    }
    companion object
    {
        const val REQUEST_CODE = 1000
    }
}
