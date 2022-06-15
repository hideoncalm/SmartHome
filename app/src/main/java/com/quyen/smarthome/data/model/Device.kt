package com.quyen.smarthome.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "device")
@Parcelize
data class Device(
    @PrimaryKey var device_id: String = "",
    var device_name: String = "",
    var device_ip_addr: String = "",
    var device_room_id: String = "",
    var device_used_power: Int = 0,
    var device_favorite: Boolean = false,
    var device_type: Int = 0,
    var device_info: String = ""
) : Parcelable {
    companion object {
        var diffUtil = object : DiffUtil.ItemCallback<Device>() {
            override fun areItemsTheSame(oldItem: Device, newItem: Device): Boolean {
                return oldItem.device_id == newItem.device_id

            }

            override fun areContentsTheSame(oldItem: Device, newItem: Device): Boolean {
                return oldItem == newItem
            }

        }
    }
}
