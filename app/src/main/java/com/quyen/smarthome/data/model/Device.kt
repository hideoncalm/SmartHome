package com.quyen.smarthome.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Device(
    var device_id: String = "",
    var device_name: String = "",
    var device_ip_addr: String = "",
    var device_room_id: String = "",
    var device_hum: Int = 0,
    var device_temp: Int = 0,
    var device_state: Int = 0,
    var device_used_power: Int = 0,
    var device_speed: Int = 0,
    var device_favorite : Boolean = false,
    var device_type : Int = 0,
    val device_info : String = ""
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
