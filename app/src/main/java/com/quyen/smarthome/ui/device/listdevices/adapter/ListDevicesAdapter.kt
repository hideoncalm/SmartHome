package com.quyen.smarthome.ui.device.listdevices.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.quyen.smarthome.base.BaseRecyclerViewAdapter
import com.quyen.smarthome.base.BaseViewHolder
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.databinding.ItemDeviceBinding

class ListDevicesAdapter(
    private val onNewItemDeviceClick: (Device) -> Unit,
) : BaseRecyclerViewAdapter<Device, ListDevicesAdapter.ListNewDevicesHolder>(
    Device.diffUtil,
    onNewItemDeviceClick
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListNewDevicesHolder = ListNewDevicesHolder(
        ItemDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onNewItemDeviceClick
    )

    class ListNewDevicesHolder(
        private val binding: ItemDeviceBinding,
        onNewItemDeviceClick: (Device) -> Unit,
    ) : BaseViewHolder<Device>(binding, onNewItemDeviceClick) {

        override fun onBindData(item: Device) {
            super.onBindData(item)
            binding.apply {
                textDeviceName.text = item.device_name
                textDeviceIP.text = item.device_ip_addr
            }
        }
    }
}
