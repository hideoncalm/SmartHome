package com.quyen.smarthome.ui.scenes.adapter

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class Scene(
    var roomName: String = "",
    var deviceName: String = "",
    var time: String = "",
    var imageURI: String = "",
) : Parcelable {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Scene>() {
            override fun areItemsTheSame(oldItem: Scene, newItem: Scene): Boolean {
                return oldItem.roomName == newItem.roomName
                        && oldItem.deviceName == newItem.deviceName
                        && oldItem.time == newItem.time
                        && oldItem.imageURI == newItem.imageURI
            }

            override fun areContentsTheSame(oldItem: Scene, newItem: Scene): Boolean {
                return oldItem == newItem
            }
        }
    }
}
