package com.quyen.smarthome.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class Home(
    var home_id: String = "",
    var home_name: String = "",
    var home_address: String = "",
    var home_total_room : Int = 1,
    var home_image: String = "https://thumbor.forbes.com/thumbor/fit-in/900x510/https://www.forbes.com/advisor/wp-content/uploads/2021/08/download-23.jpg"
) : Parcelable {
    companion object {
        var diffUtil = object : DiffUtil.ItemCallback<Home>() {
            override fun areItemsTheSame(oldItem: Home, newItem: Home): Boolean {
                return oldItem.home_id == newItem.home_id
            }

            override fun areContentsTheSame(oldItem: Home, newItem: Home): Boolean {
                return oldItem == newItem
            }

        }
    }
}

