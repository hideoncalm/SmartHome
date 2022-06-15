package com.quyen.smarthome.ui.room.listrooms.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.quyen.smarthome.base.BaseRecyclerViewAdapter
import com.quyen.smarthome.base.BaseViewHolder
import com.quyen.smarthome.data.model.Room
import com.quyen.smarthome.databinding.ItemRoomBinding
import com.quyen.smarthome.utils.loadImageFromUrl

class ListItemHomeAdapter(
    private val onItemRoomClick: (Room) -> Unit,
    private val onSwitchItemClick: (Room) -> Unit
) : BaseRecyclerViewAdapter<Room, ListItemHomeAdapter.ListItemHolder>(
    Room.diffUtil,
    onItemRoomClick
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListItemHolder = ListItemHolder(
        ItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onItemRoomClick,
        onSwitchItemClick
    )

    class ListItemHolder(
        private val binding: ItemRoomBinding,
        onItemRoomClick: (Room) -> Unit,
        private val onSwitchItemClick: (Room) -> Unit
    ) : BaseViewHolder<Room>(binding, onItemRoomClick) {

        override fun onBindData(item: Room) {
            super.onBindData(item)
            binding.apply {
                imageDevice.loadImageFromUrl(item.room_image)
                textLocation.text = item.room_name
                textTotalDevice.text = "${item.room_total_device} Devices"
                switchDevice.isChecked = item.room_all_device
                switchDevice.setOnClickListener { onSwitchItemClick(item) }
            }
        }
    }
}
