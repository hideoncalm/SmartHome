package com.quyen.smarthome.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var account: String = "",
    var password: String = "",
    var name: String = "",
    var imageURI: String = "",
    var homeId: String = "",
    var id: Int = (System.currentTimeMillis() % Int.MAX_VALUE).toInt(),
    ) : Parcelable {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}
