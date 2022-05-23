package com.quyen.smarthome.ui.room.listrooms

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.R
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.data.model.Room
import com.quyen.smarthome.databinding.FragmentRoomsBinding
import com.quyen.smarthome.ui.room.listrooms.adapter.ListItemHomeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentListItem : BaseFragment<FragmentRoomsBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRoomsBinding =
        FragmentRoomsBinding::inflate
    private val roomViewModel: FragmentListRoomViewModel by viewModels()

    private val roomAdapter: ListItemHomeAdapter by lazy {
        ListItemHomeAdapter(this::onItemHomeClick, onSwitchClick)
    }

    override fun initViews() {
        binding.recyclerItem.adapter = roomAdapter
    }

    override fun initData() {
        roomViewModel.rooms.observe(viewLifecycleOwner, {
            roomAdapter.updateData(it)
        })
    }

    private fun onItemHomeClick(room: Room) {
        findNavController().navigate(R.id.action_fragmentListItem_to_roomDetailFragment)
    }

    private val onSwitchClick = object : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {

        }

    }
}
