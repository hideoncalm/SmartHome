package com.quyen.smarthome.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserControlRelay(
    var id: String = "",
    var userId: String = "",
    var UserControlRelayId: String = ""
) : Parcelable {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<UserControlRelay>() {
            override fun areItemsTheSame(
                oldItem: UserControlRelay,
                newItem: UserControlRelay
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UserControlRelay,
                newItem: UserControlRelay
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}
