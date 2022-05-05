package com.quyen.smarthome.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class Home(
    var id: String = "",
    var name: String = "",
    var address: String = "",
    var homeId: String = ""
) : Parcelable {
    companion object {
        var diffUtil = object : DiffUtil.ItemCallback<Home>() {
            override fun areItemsTheSame(oldItem: Home, newItem: Home): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Home, newItem: Home): Boolean {
                return oldItem == newItem
            }

        }
    }
}

