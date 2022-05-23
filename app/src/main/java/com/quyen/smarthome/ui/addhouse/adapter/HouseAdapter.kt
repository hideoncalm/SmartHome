package com.quyen.smarthome.ui.addhouse.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.quyen.smarthome.base.BaseRecyclerViewAdapter
import com.quyen.smarthome.base.BaseViewHolder
import com.quyen.smarthome.data.model.Home
import com.quyen.smarthome.databinding.ItemHouseBinding
import com.quyen.smarthome.utils.loadImageFromUrl

class HouseAdapter(
    private val onHomeItemClick: (Home) -> Unit
) : BaseRecyclerViewAdapter<Home, HouseAdapter.HomeHolder>(Home.diffUtil, onHomeItemClick)
{
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeHolder = HomeHolder(
        ItemHouseBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onHomeItemClick
    )

    class HomeHolder(
        private val binding: ItemHouseBinding,
        onHomeItemClick: (Home) -> Unit,
    ) : BaseViewHolder<Home>(binding, onHomeItemClick) {

        override fun onBindData(item: Home) {
            super.onBindData(item)
            binding.apply {
                imageHome.loadImageFromUrl(item.home_image)
                textHouseName.text = item.home_name
                textTotalRooms.text = "${item.home_total_room} Rooms"
                textAddress.text = item.home_address
            }
        }
    }
}
