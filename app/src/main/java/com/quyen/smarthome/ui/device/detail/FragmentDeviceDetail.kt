package com.quyen.smarthome.ui.device.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.R
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.data.model.DeviceTime
import com.quyen.smarthome.databinding.FragmentDeviceDetailBinding
import com.quyen.smarthome.ui.device.detail.adapter.DeviceTimeAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FragmentDeviceDetail : BaseFragment<FragmentDeviceDetailBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDeviceDetailBinding =
        FragmentDeviceDetailBinding::inflate

    private val viewModel: FragmentDeviceDetailViewModel by viewModels()
    private var device: Device? = null
    private val backgroundOn = R.drawable.bg_circle_button_on
    private val backgroundOff = R.drawable.bg_circle_button
    private val timeAdapter by lazy {
        DeviceTimeAdapter(::onItemTimeClick)
    }

    override fun initViews() {
        binding.apply {
            recyclerUsedTime.adapter = timeAdapter
            buttonBack.setOnClickListener {
                findNavController().popBackStack()
            }
            buttonOnOff.setOnClickListener {
                // call api on/off
                if (viewModel.isOn.value == false) {
                    device?.let { it -> viewModel.turnDeviceOn(it) }
                } else {
                    device?.let { it -> viewModel.turnDeviceOff(it) }
                }
            }
            buttonAlarm.setOnClickListener {
                // call api on/off when exact time
            }
            buttonCounter.setOnClickListener {
                // call api on/off timer
            }
        }
        viewModel.isOn.observe(viewLifecycleOwner, {
            if(it) {
                binding.buttonOnOff.background =
                    activity?.let { activity -> ContextCompat.getDrawable(activity, backgroundOn) }
            }
            else {
                binding.buttonOnOff.background =
                    activity?.let { activity -> ContextCompat.getDrawable(activity, backgroundOff) }
            }
        })
    }

    override fun initData() {
        device = arguments?.getParcelable(DEVICE_KEY)
        device?.let {
            viewModel.subscribeMqttDevice(it)
        }
        viewModel.useTimes.observe(viewLifecycleOwner, {
            timeAdapter.updateData(it)
        })
    }

    private fun onItemTimeClick(time: DeviceTime) {

    }

    companion object {
        private const val DEVICE_KEY = "device"
    }
}
