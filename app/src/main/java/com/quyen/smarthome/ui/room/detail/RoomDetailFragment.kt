package com.quyen.smarthome.ui.room.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.data.model.Room
import com.quyen.smarthome.databinding.FragmentRoomDetailBinding
import com.quyen.smarthome.ui.home.adapter.FavoriteDeviceAdapter
import com.quyen.smarthome.utils.Constant.ROOM_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomDetailFragment : BaseFragment<FragmentRoomDetailBinding>() {

    lateinit var room: Room
    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRoomDetailBinding =
        FragmentRoomDetailBinding::inflate
    private val viewModel: RoomDetailViewModel by viewModels()

    private val deviceAdapter by lazy {
        FavoriteDeviceAdapter(::onItemDeviceClick)
    }

    override fun initViews() {
        room = arguments?.getParcelable(ROOM_KEY)!!
        viewModel.subscribeMqttDevice(room)
        binding.apply {
            buttonBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        binding.apply {
            textToolbarTitle.text = room.room_name
            textRoomName.text = room.room_name
            switchAllDevice.isChecked = room.room_all_device
        }
        binding.recyclerItem.adapter = deviceAdapter
        binding.switchAllDevice.setOnClickListener { onSwitchAllDeviceClick() }
    }

    override fun initData() {
        viewModel.getDevicesOffRoom(room)
        viewModel.devices.observe(this, {
            binding.textDevices.text = "${it.size} $DEVICES"
            deviceAdapter.updateData(it)
        })
        viewModel.isRoomOn.observe(viewLifecycleOwner, {
            binding.switchAllDevice.isChecked = it
        })
    }

    private fun onItemDeviceClick(device: Device) {
        val action =
            RoomDetailFragmentDirections.actionRoomDetailFragmentToFragmentDeviceDetail(device)
        findNavController().navigate(action)
    }

    private fun onSwitchAllDeviceClick() {
        if (viewModel.isRoomOn.value == true) {
            viewModel.turnAllDeviceOfRoomOff()
        } else {
            viewModel.turnAllDeviceOffRoomOn()
        }
    }

    companion object {
        private const val DEVICES = "Devices"
    }
}
