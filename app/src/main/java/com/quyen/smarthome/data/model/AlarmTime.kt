package com.quyen.smarthome.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "alarm")
data class AlarmTime(
    val hour: Int = 0,
    val minute: Int = 0,
    val mon: Int = 0,
    val tue: Int = 0,
    val wed: Int = 0,
    val thus: Int = 0,
    val fri: Int = 0,
    val sat: Int = 0,
    val sun: Int = 0,
    val content : String? = "",
    val state : Int = 0,
    @PrimaryKey val requestCode: Int = (System.currentTimeMillis() % Int.MAX_VALUE).toInt()

) : Parcelable {
    companion object {
        var diffUtil = object : DiffUtil.ItemCallback<AlarmTime>() {
            override fun areItemsTheSame(oldItem: AlarmTime, newItem: AlarmTime): Boolean {
                return oldItem.requestCode == newItem.requestCode
            }

            override fun areContentsTheSame(oldItem: AlarmTime, newItem: AlarmTime): Boolean {
                return oldItem == newItem
            }

        }
    }
}
