package com.quyen.smarthome.ui.scenes.adapter

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize


@Parcelize
data class Scene(
    @ColumnInfo(name = "device_id") var deviceId : String = "",
    @ColumnInfo(name = "room_name") var roomName: String = "",
    @ColumnInfo(name = "device_name") var deviceName: String = "",
    @ColumnInfo(name = "hour") var hour: Int = 0,
    @ColumnInfo(name = "minute") var minute: Int = 0,
    @ColumnInfo(name = "room_image") var imageURI: String = ""
) : Parcelable {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Scene>() {
            override fun areItemsTheSame(oldItem: Scene, newItem: Scene): Boolean {
                return oldItem.roomName == newItem.roomName
                        && oldItem.deviceName == newItem.deviceName
                        && oldItem.hour == newItem.hour
                        && oldItem.minute == newItem.minute
                        && oldItem.imageURI == newItem.imageURI
                        && oldItem.deviceId == newItem.deviceId
            }

            override fun areContentsTheSame(oldItem: Scene, newItem: Scene): Boolean {
                return oldItem == newItem
            }
        }
    }
}
