package com.quyen.smarthome.ui.device.listdevices

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.databinding.FragmentListDeviceBinding
import com.quyen.smarthome.ui.device.listdevices.adapter.ListDevicesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListDevicesFragment : BaseFragment<FragmentListDeviceBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentListDeviceBinding =
        FragmentListDeviceBinding::inflate

    private val newDeviceAdapter by lazy {
        ListDevicesAdapter(::onItemClick, ::onButtonFavoriteClick, ::onSwitchClick)
    }

    private val viewModel: ListDevicesViewModel by viewModels()

    override fun initViews() {
        binding.recyclerItem.adapter = newDeviceAdapter
    }

    override fun initData() {
        viewModel.devices.observe(viewLifecycleOwner, {
            newDeviceAdapter.updateData(it as MutableList<Device>)
            for (device in it) {
                viewModel.subscribeMqttDevice(device)
            }
        })
    }

    private fun onItemClick(item: Device) {
        val action =
            ListDevicesFragmentDirections.actionListDevicesFragmentToFragmentDeviceDetail(item)
        findNavController().navigate(action)
    }

    private fun onButtonFavoriteClick(item: Device) {
        val device = item.copy()
        device.device_favorite = item.device_favorite != true
        viewModel.updateDevice(device)
    }

    private fun onSwitchClick(item: Device) {
        if (item.device_info == ON) viewModel.turnDeviceOff(item) else viewModel.turnDeviceOn(item)
    }

    companion object {
        private const val ON = "on"
    }
}
