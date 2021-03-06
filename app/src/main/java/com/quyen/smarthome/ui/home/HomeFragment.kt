package com.quyen.smarthome.ui.home

import android.content.Intent
import android.net.wifi.WifiManager
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.R
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.data.model.Home
import com.quyen.smarthome.data.model.Room
import com.quyen.smarthome.databinding.FragmentHomeBinding
import com.quyen.smarthome.ui.home.adapter.FavoriteDeviceAdapter
import com.quyen.smarthome.ui.room.listrooms.adapter.ListItemHomeAdapter
import com.quyen.smarthome.utils.Constant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate

    private var home : Home? = null
    override val viewModel: HomeViewModel by viewModels()
    private val deviceAdapter: FavoriteDeviceAdapter by lazy {
        FavoriteDeviceAdapter(::onItemDeviceClick)
    }
    private val roomAdapter: ListItemHomeAdapter by lazy {
        ListItemHomeAdapter(::onItemRoomClick, ::onSwitchClick)
    }

    private val mResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { _ ->
        findNavController().navigate(R.id.action_homeFragment_to_fragmentAddDevice)
    }

    override fun initViews() {
        home = arguments?.getParcelable(Constant.HOME_KEY)
        home?.let {
            viewModel.getRoomsByHomeId(it)
        }
        binding.apply {
            buttonAddDevice.setOnClickListener {
                val intent = Intent(WifiManager.ACTION_PICK_WIFI_NETWORK)
                mResultLauncher.launch(intent)
            }
            buttonManagerHome.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_fragmentAddHouse)
            }
            recyclerFavorite.adapter = deviceAdapter
            recyclerRoom.adapter = roomAdapter
        }
    }

    override fun initData() {
        viewModel.devices.observe(viewLifecycleOwner, {
            deviceAdapter.updateData(it as MutableList<Device>)
        })
        viewModel.rooms.observe(viewLifecycleOwner, {
            roomAdapter.updateData(it)
        })
    }

    private fun onItemDeviceClick(device: Device) {
        val directions = HomeFragmentDirections.actionHomeFragmentToFragmentDeviceDetail(device)
        findNavController().navigate(directions)
    }

    private fun onItemRoomClick(room: Room) {
        val action = HomeFragmentDirections.actionHomeFragmentToRoomDetailFragment(room)
        findNavController().navigate(action)
    }

    private fun onSwitchClick(room: Room) {
    }
}
