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
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.R
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.databinding.FragmentAddDeviceBinding
import com.quyen.smarthome.service.setupAndroidMqttClient
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FragmentAddDevice : BaseFragment<FragmentAddDeviceBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddDeviceBinding =
        FragmentAddDeviceBinding::inflate

    private val viewModel: FragmentAddDeviceViewModel by viewModels()

    private var wifiManager: WifiManager? = null
    private var results: List<ScanResult>? = null
    private val arrayList = arrayListOf<String>()
    private var spinnerAdapter: ArrayAdapter<String>? = null
    private var wifidapter: ArrayAdapter<String>? = null
    private var wifiSSID = ""
    private var wifiPassword = ""

    private val spinnerListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, pos: Int, id: Long) {
            Timber.d(" wifi add device : ${adapterView?.getItemAtPosition(pos).toString()}")
        }


        override fun onNothingSelected(p0: AdapterView<*>?) {
        }

    }

    override fun initViews() {
        spinnerAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            arrayList
        )
        wifidapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            arrayList
        )
        binding.spinnerWifi.adapter = spinnerAdapter
        binding.spinnerChooseWifi.adapter = wifidapter
        binding.spinnerChooseWifi.onItemSelectedListener = spinnerListener
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.buttonContinue.setOnClickListener {
            viewModel.registerDevice(wifiSSID, wifiPassword)
            setupAndroidMqttClient(requireContext())
        }

        viewModel.loading.observe(viewLifecycleOwner, {
            binding.progressBar.isVisible = it
        })

        viewModel.response.observe(viewLifecycleOwner, {
            if(it.isSuccessful)
            {
                findNavController().navigate(R.id.action_fragmentAddDevice_to_fragmentDeviceDetail)
            }
        })
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
        context!!.registerReceiver(
            wifiReceiver,
            IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        )
        wifiManager!!.startScan()
    }


    private var wifiReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            results = wifiManager!!.scanResults
            context.unregisterReceiver(this)
            for (scanResult in results!!) {
                var wifi_ssid = scanResult.SSID
                Timber.d("WIFIScannerActivity: WIFI SSID: $wifi_ssid")
                arrayList.add(scanResult.SSID)
            }
            spinnerAdapter!!.notifyDataSetChanged()
            wifidapter!!.notifyDataSetChanged()
        }
    }

}
