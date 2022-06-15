package com.quyen.smarthome.ui.device.listdevices.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import com.quyen.smarthome.R
import com.quyen.smarthome.base.BaseRecyclerViewAdapter
import com.quyen.smarthome.base.BaseViewHolder
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.databinding.ItemDeviceDetailBinding

class ListDevicesAdapter(
    private val onNewItemDeviceClick: (Device) -> Unit,
    private val onButtonFavoriteClick: (Device) -> Unit,
    private val onSwitchItemClick: (Device) -> Unit
) : BaseRecyclerViewAdapter<Device, ListDevicesAdapter.ListNewDevicesHolder>(
    Device.diffUtil,
    onNewItemDeviceClick
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListNewDevicesHolder = ListNewDevicesHolder(
        ItemDeviceDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onNewItemDeviceClick, onButtonFavoriteClick, onSwitchItemClick
    )

    class ListNewDevicesHolder(
        private val binding: ItemDeviceDetailBinding,
        onNewItemDeviceClick: (Device) -> Unit,
        val onButtonFavoriteClick: (Device) -> Unit,
        private val onSwitchItemClick: (Device) -> Unit
    ) : BaseViewHolder<Device>(binding, onNewItemDeviceClick) {

        override fun onBindData(item: Device) {
            super.onBindData(item)
            binding.apply {
                textDeviceName.text = item.device_name
                textRoomName.text = item.device_ip_addr
                buttonFavorite.apply {
                    if (item.device_favorite) setImageResource(R.drawable.ic_favorite) else setImageResource(
                        R.drawable.ic_unfavorite
                    )
                }
                switchDevice.isChecked = item.device_info.lowercase() == ON
            }
            binding.buttonFavorite.setOnClickListener {
                onButtonFavoriteClick(item)
            }
            binding.switchDevice.setOnClickListener{
                onSwitchItemClick(item)
            }
        }
    }

    companion object {
        private const val ON = "on"
    }
}
