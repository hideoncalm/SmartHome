package com.quyen.smarthome.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "room")
data class Room(
    @PrimaryKey var room_id: String = "",
    var room_name: String? = "Living Room",
    var room_home_id: String = "",
    var room_total_device: Int? = 0,
    var room_all_device: Boolean =  false,
    var room_image: String = ""
) : Parcelable {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Room>() {
            override fun areItemsTheSame(oldItem: Room, newItem: Room): Boolean {
                return oldItem.room_id == newItem.room_id
            }

            override fun areContentsTheSame(oldItem: Room, newItem: Room): Boolean {
                return oldItem == newItem
            }
        }
    }
}
