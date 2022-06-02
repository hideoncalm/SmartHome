package com.quyen.smarthome.ui.device.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.R
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.databinding.FragmentDeviceDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentDeviceDetail : BaseFragment<FragmentDeviceDetailBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDeviceDetailBinding =
        FragmentDeviceDetailBinding::inflate

    private val viewModel : FragmentDeviceDetailViewModel by viewModels()
    private var device : Device? = null


    override fun initViews() {
        binding.apply {
            buttonBack.setOnClickListener {
                findNavController().popBackStack()
            }
            buttonOnOff.setOnClickListener {
                // call api on/off
                device?.let { it -> viewModel.turnDeviceOn(it) }
            }
            buttonAlarm.setOnClickListener {
                // call api on/off when exact time
                device?.let { it -> viewModel.turnDeviceOff(it) }
            }
            buttonCounter.setOnClickListener {
                // call api on/off timer
            }
        }
    }

    override fun initData() {
        device = arguments?.getParcelable(DEVICE_KEY)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.disConnectMqtt()
    }

    companion object{
        private const val DEVICE_KEY = "device"
    }
}
