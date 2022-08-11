package com.quyen.smarthome.ui.room.listrooms

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.R
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.data.model.Room
import com.quyen.smarthome.databinding.FragmentRoomsBinding
import com.quyen.smarthome.ui.room.listrooms.adapter.ListItemHomeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentListItem : BaseFragment<FragmentRoomsBinding, FragmentListRoomViewModel>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRoomsBinding =
        FragmentRoomsBinding::inflate

    override val viewModel: FragmentListRoomViewModel by viewModels()

    private val roomAdapter: ListItemHomeAdapter by lazy {
        ListItemHomeAdapter(::onItemHomeClick, ::onSwitchClick)
    }

    override fun initViews() {
        binding.recyclerItem.adapter = roomAdapter

        binding.buttonAddRoom.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentListItem_to_fragmentCreateRoom)
        }
    }

    override fun initData() {
        viewModel.rooms.observe(viewLifecycleOwner, {
            roomAdapter.updateData(it as MutableList<Room>)
        })
    }

    private fun onItemHomeClick(room: Room) {
        val action = FragmentListItemDirections.actionFragmentListItemToRoomDetailFragment(room)
        findNavController().navigate(action)
    }

    private fun onSwitchClick(room: Room) {
        findNavController().navigate(R.id.action_fragmentListItem_to_roomDetailFragment)
    }

}
