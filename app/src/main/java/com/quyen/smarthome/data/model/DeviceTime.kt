package com.quyen.smarthome.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "active_time")
@Parcelize
data class DeviceTime(
    @PrimaryKey
    @ColumnInfo(name = "time")
    var time: String? = "",
    @ColumnInfo(name = "device_id") var deviceID: String? = "",
    @ColumnInfo(name = "state") var state: Int = 0
) : Parcelable {
    companion object {
        var diffUtil = object : DiffUtil.ItemCallback<DeviceTime>() {
            override fun areItemsTheSame(oldItem: DeviceTime, newItem: DeviceTime): Boolean {
                return oldItem.time == newItem.time
            }

            override fun areContentsTheSame(oldItem: DeviceTime, newItem: DeviceTime): Boolean {
                return oldItem == newItem
            }
        }
    }
}
