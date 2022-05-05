package com.quyen.smarthome.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
    var id: String = "",
    var name: String? = "Living Room",
    var homeId: String = "",
    var totalDevice: Int? = 0,
    var stateAllDevices: Boolean = false,
    var roomImage: String = ""
) : Parcelable {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Room>() {
            override fun areItemsTheSame(oldItem: Room, newItem: Room): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Room, newItem: Room): Boolean {
                return oldItem == newItem
            }
        }
    }
}
