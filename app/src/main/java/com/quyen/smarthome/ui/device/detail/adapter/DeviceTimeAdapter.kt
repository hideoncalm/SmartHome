package com.quyen.smarthome.ui.device.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.quyen.smarthome.R
import com.quyen.smarthome.base.BaseRecyclerViewAdapter
import com.quyen.smarthome.base.BaseViewHolder
import com.quyen.smarthome.data.model.DeviceTime
import com.quyen.smarthome.databinding.ItemUsedTimeBinding
import com.quyen.smarthome.utils.Constant.STATE_OFF

class DeviceTimeAdapter(
    private val onItemDeviceTimeClick: (DeviceTime) -> Unit,
) : BaseRecyclerViewAdapter<DeviceTime, DeviceTimeAdapter.DeviceTimeHolder>(
    DeviceTime.diffUtil,
    onItemDeviceTimeClick
) {

    override fun submitList(list: MutableList<DeviceTime>?) {
        val result = arrayListOf<DeviceTime>()
        list?.forEach { result.add(it.copy()) }
        result.reverse()
        super.submitList(result)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeviceTimeHolder = DeviceTimeHolder(
        ItemUsedTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onItemDeviceTimeClick
    )

    class DeviceTimeHolder(
        private val binding: ItemUsedTimeBinding,
        onItemDeviceTimeClick: (DeviceTime) -> Unit,
    ) : BaseViewHolder<DeviceTime>(binding, onItemDeviceTimeClick) {

        override fun onBindData(item: DeviceTime) {
            super.onBindData(item)
            binding.textTimeOn.apply {
                text = item.time
                if (item.state == STATE_OFF) {
                    setTextColor(resources.getColor(R.color.red_F44336))
                } else {
                    setTextColor(resources.getColor(R.color.green_388E3C))
                }
            }
        }
    }
}








