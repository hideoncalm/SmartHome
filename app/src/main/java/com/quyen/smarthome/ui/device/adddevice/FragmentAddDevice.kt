package com.quyen.smarthome.ui.device.adddevice

import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.databinding.FragmentAddDeviceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentAddDevice : BaseFragment<FragmentAddDeviceBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddDeviceBinding =
        FragmentAddDeviceBinding::inflate

    private var wifiManager : WifiManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun initViews() {
        binding.buttonContinue.setOnClickListener {

        }
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initData() {
    }
}
