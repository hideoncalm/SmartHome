package com.quyen.smarthome.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
    val id: String,
    val name: String? = "Living Room",
    val homeId: String,
    val totalDevice: Int? = 0,
    val switchState: Boolean = false,
    val iconUrl: String
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
