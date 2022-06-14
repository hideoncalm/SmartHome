package com.quyen.smarthome.ui.scenes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.quyen.smarthome.base.BaseRecyclerViewAdapter
import com.quyen.smarthome.base.BaseViewHolder
import com.quyen.smarthome.databinding.ItemSceneBinding
import com.quyen.smarthome.utils.loadImageFromUrl

class SceneAdapter(
    private val onItemClick: (Scene) -> Unit,
    private val onButtonDeleteClick: (Scene) -> Unit
) : BaseRecyclerViewAdapter<Scene, SceneAdapter.SceneHolder>(Scene.diffUtil, onItemClick) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SceneHolder {
        return SceneHolder(
            ItemSceneBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemClick, onButtonDeleteClick
        )
    }

    class SceneHolder(
        private val binding: ItemSceneBinding,
        private val onItemClick: (Scene) -> Unit,
        private val onButtonDeleteClick: (Scene) -> Unit
    ) : BaseViewHolder<Scene>(binding, onItemClick) {

        override fun onBindData(item: Scene) {
            super.onBindData(item)
            binding.apply {
                buttonDelete.setOnClickListener {
                    onButtonDeleteClick(item)
                }
                imageDevice.loadImageFromUrl(item.imageURI)
                textDeviceName.text = item.deviceName
                textRoomName.text = item.roomName
                textTime.text = "${item.hour}:${item.minute}"
            }
        }
    }
}
