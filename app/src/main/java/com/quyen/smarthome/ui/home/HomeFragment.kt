package com.quyen.smarthome.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.R
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.data.model.Room
import com.quyen.smarthome.databinding.FragmentHomeBinding
import com.quyen.smarthome.ui.home.adapter.FavoriteDeviceAdapter
import com.quyen.smarthome.ui.room.listrooms.adapter.ListItemHomeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate

    private val homeViewModel: HomeViewModel by viewModels()
    private val deviceAdapter: FavoriteDeviceAdapter by lazy {
        FavoriteDeviceAdapter(::onItemDeviceClick)
    }
    private val roomAdapter: ListItemHomeAdapter by lazy {
        ListItemHomeAdapter(::onItemRoomClick, onSwitchClick)
    }

    override fun initViews() {
        binding.apply {
            buttonAddDevice.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_fragmentAddDevice)
            }
            buttonManagerHome.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_fragmentAddHouse)
            }
            recyclerFavorite.adapter = deviceAdapter
            recyclerRoom.adapter = roomAdapter
        }
    }

    override fun initData() {
        homeViewModel.devices.observe(viewLifecycleOwner, {
            deviceAdapter.updateData(it)
        })
        homeViewModel.rooms.observe(viewLifecycleOwner, {
            roomAdapter.updateData(it)
        })
    }

    private fun onItemDeviceClick(device: Device) {
        findNavController().navigate(R.id.action_homeFragment_to_fragmentDeviceDetail)
    }

    private fun onItemRoomClick(room: Room) {
        findNavController().navigate(R.id.action_homeFragment_to_roomDetailFragment)
    }

    private val onSwitchClick = object : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {

        }
    }
}
