package com.quyen.smarthome.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.quyen.smarthome.utils.Constant.MONDAY
import com.quyen.smarthome.utils.Constant.NOT_REPEAT
import kotlinx.parcelize.Parcelize
import java.time.DayOfWeek
import java.time.MonthDay
import java.util.*

@Parcelize
@Entity(tableName = "alarm")
data class AlarmTime(
    @ColumnInfo(name = "device_id") val deviceId: String = "",
    @ColumnInfo(name = "hour") var hour: Int = 0,
    @ColumnInfo(name = "minute") var minute: Int = 0,
    @ColumnInfo(name = "state") val state: Int = 0,
    @ColumnInfo(name = "day_of_week") val dayOfWeek: Int = NOT_REPEAT,
    @ColumnInfo(name = "content") val content: String? = "",
    @PrimaryKey @ColumnInfo(name = "request_code") val requestCode: Int = (System.currentTimeMillis() % Int.MAX_VALUE).toInt()

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
