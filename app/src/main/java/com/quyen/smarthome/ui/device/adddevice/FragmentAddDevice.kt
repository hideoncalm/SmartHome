package com.quyen.smarthome.ui.device.adddevice

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.R
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.databinding.FragmentAddDeviceBinding
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
    private var wifiSSID = "quyenHaHa"
    private var wifiPassword = "0966733413"
    private var wifiBSSID = ""

    private val spinnerListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, pos: Int, id: Long) {
            wifiSSID = adapterView?.getItemAtPosition(pos).toString()
        }


        override fun onNothingSelected(p0: AdapterView<*>?) {
        }

    }

    override fun initViews() {
        spinnerAdapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.item_spinner,
            arrayList
        )

        binding.spinnerWifi.adapter = spinnerAdapter
        binding.spinnerWifi.onItemSelectedListener = spinnerListener
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.buttonContinue.setOnClickListener {
            wifiPassword = binding.editPasswordWifi.text.toString()
            Toast.makeText(requireContext(), wifiSSID + wifiPassword, Toast.LENGTH_LONG).show()
            viewModel.connectToWfi(wifiSSID, wifiPassword)
        }

        viewModel.loading.observe(viewLifecycleOwner, {
            binding.progressBar.isVisible = it
            if (!it) {
                viewModel.device.value?.let { device ->
                    val direction =
                        FragmentAddDeviceDirections.actionFragmentAddDeviceToFragmentDeviceDetail(
                            device
                        )
                    findNavController().navigate(direction)
                }
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
        // get current wifi connected
        val wifiInfo : WifiInfo = wifiManager!!.connectionInfo
        wifiInfo?.let {
            viewModel.wifiBSSID = it.ssid
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context!!.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
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
                val wifi_ssid = scanResult.SSID
                val cap = scanResult.capabilities
                Timber.d("WIFIScannerActivity: WIFI SSID: $wifi_ssid capacity : $cap")
                arrayList.add(scanResult.SSID)
            }
            spinnerAdapter!!.notifyDataSetChanged()
        }
    }

}
