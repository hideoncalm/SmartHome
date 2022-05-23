package com.quyen.smarthome.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.quyen.smarthome.base.BaseRecyclerViewAdapter
import com.quyen.smarthome.base.BaseViewHolder
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.databinding.ItemDeviceBinding

class FavoriteDeviceAdapter(
    private val onItemDeviceClick: (Device) -> Unit,
) : BaseRecyclerViewAdapter<Device, FavoriteDeviceAdapter.FavoriteDevicesHolder>(
    Device.diffUtil,
    onItemDeviceClick
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteDevicesHolder = FavoriteDevicesHolder(
        ItemDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onItemDeviceClick
    )

    class FavoriteDevicesHolder(
        private val binding: ItemDeviceBinding,
        onItemDeviceClick: (Device) -> Unit,
    ) : BaseViewHolder<Device>(binding, onItemDeviceClick) {

        override fun onBindData(item: Device) {
            super.onBindData(item)
            binding.apply {
                textDeviceName.text = item.device_name
                textRoomName.text = "Living Room"
            }
        }
    }
}
