package com.quyen.smarthome.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
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
    val deviceId : String = "",
    var hour: Int = 0,
    var minute: Int = 0,
    val state: Int = 0,
//    val monday : Int = 0,
//    val tus : Int = 0,
//    val wed : Int = 0,
//    val thus : Int = 0,
//    val fri : Int = 0,
//    val sat : Int = 0,
//    val sun : Int = 0,
    val dayOfWeek: Int = NOT_REPEAT,
    val content: String? = "",
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
