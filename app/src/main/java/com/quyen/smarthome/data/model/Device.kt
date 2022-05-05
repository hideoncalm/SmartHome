package com.quyen.smarthome.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class Device(
    var id: String = "",
    var name: String = "",
    var ipAddr: String = "",
    var roomId: Int = 0,
    var hum: Int = 0,
    var temp: Int = 0,
    var state: Int = 0,
    var usedPower: Int = 0,
    var speed: Int = 0,
) : Parcelable {
    companion object {
        var diffUtil = object : DiffUtil.ItemCallback<Device>() {
            override fun areItemsTheSame(oldItem: Device, newItem: Device): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Device, newItem: Device): Boolean {
                return oldItem == newItem
            }

        }
    }
}
