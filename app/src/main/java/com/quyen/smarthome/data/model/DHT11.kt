package com.quyen.smarthome.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class DHT11(
    var id: String = "",
    var temperature: Double = -1.0,
    var humidity: Double = -1.0,
    var roomId: String = ""
) : Parcelable {
    companion object {
        var diffUtil = object : DiffUtil.ItemCallback<DHT11>() {
            override fun areItemsTheSame(oldItem: DHT11, newItem: DHT11): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DHT11, newItem: DHT11): Boolean {
                return oldItem == newItem
            }
        }
    }
}