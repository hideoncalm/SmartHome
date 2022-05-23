package com.quyen.smarthome.ui.device.listdevices

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.R
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
        ListDevicesAdapter(::onItemClick)
    }

    private val viewModel: ListDevicesViewModel by viewModels()

    override fun initViews() {
        binding.recyclerItem.adapter = newDeviceAdapter
    }

    override fun initData() {
        viewModel.devices.observe(viewLifecycleOwner, {
            newDeviceAdapter.updateData(it)
        })
    }

    private fun onItemClick(item: Device) {
        // go to the fragment device detail
        findNavController().navigate(R.id.action_listDevicesFragment_to_fragmentDeviceDetail)
    }
}
