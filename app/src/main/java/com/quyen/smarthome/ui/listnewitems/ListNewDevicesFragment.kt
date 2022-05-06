package com.quyen.smarthome.ui.listnewitems

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.base.OnItemClickListener
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.databinding.FragmentListNewDeviceBinding
import com.quyen.smarthome.ui.listnewitems.adapter.ListNewDevicesAdapter


class ListNewDevicesFragment : BaseFragment<FragmentListNewDeviceBinding>(), OnItemClickListener<Device> {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentListNewDeviceBinding =
        FragmentListNewDeviceBinding::inflate

    private val newDeviceAdapter = ListNewDevicesAdapter(this::onItemClick)
    private val viewModel : ListNewDevicesViewModel by viewModels()

    override fun initViews() {
        binding.recyclerItem.adapter = newDeviceAdapter
    }

    override fun initData() {
        viewModel.devices.observe( viewLifecycleOwner, {
            newDeviceAdapter.updateData(it)
        })
    }

    override fun onItemClick(item: Device) {
        Toast.makeText(requireContext(), "Item ${item.device_ip_addr} Click", Toast.LENGTH_LONG)
    }
}