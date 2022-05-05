package com.quyen.smarthome.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class Relay(
    var id: String = "",
    var status: Boolean = false,
    var roomId: String = ""
) : Parcelable {
    companion object {
        var diffUtil = object : DiffUtil.ItemCallback<Relay>() {
            override fun areItemsTheSame(oldItem: Relay, newItem: Relay): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Relay, newItem: Relay): Boolean {
                return oldItem == newItem
            }

        }
    }
}
