package com.quyen.smarthome.ui.listitempager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import com.quyen.smarthome.base.BaseRecyclerViewAdapter
import com.quyen.smarthome.base.BaseViewHolder
import com.quyen.smarthome.data.model.Room
import com.quyen.smarthome.databinding.ItemRoomBinding
import com.quyen.smarthome.utils.loadImageFromUrl

class ListItemHomeAdapter(
    private val onItemRoomClick: (Room) -> Unit,
    private val onSwitchItemClick: CompoundButton.OnCheckedChangeListener
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
        private val onSwitchItemClick: CompoundButton.OnCheckedChangeListener
    ) : BaseViewHolder<Room>(binding, onItemRoomClick) {

        override fun onBindData(item: Room) {
            super.onBindData(item)
            binding.apply {
                imageDevice.loadImageFromUrl(item.iconUrl)
                textLocation.text = item.name
                textTotalDevice.text = textTotalDevice.toString()
                switchDevice.setOnCheckedChangeListener(onSwitchItemClick)
            }
        }
    }
}
