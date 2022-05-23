package com.quyen.smarthome.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.data.model.Room
import com.quyen.smarthome.data.source.remote.DeviceRemoteDataSource
import com.quyen.smarthome.data.source.remote.RoomRemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val deviceRepo : DeviceRemoteDataSource,
    private val roomRepo : RoomRemoteDataSource
) : ViewModel() {

    private val _devices = MutableLiveData<MutableList<Device>>()
    val devices: LiveData<MutableList<Device>>
        get() = _devices

    private val _rooms = MutableLiveData<MutableList<Room>>()
    val rooms: LiveData<MutableList<Room>>
        get() = _rooms

    init {
        insertDevice()
        getDevices()
        getRooms()
    }

    private fun getDevices(){
        viewModelScope.launch {
            _devices.postValue(deviceRepo.getDevicesByRoomId(0) as MutableList<Device>?)
        }
    }

    private fun getRooms()
    {
        viewModelScope.launch {
            _rooms.postValue(roomRepo.getRooms() as MutableList<Room>?)
        }
    }

    private fun insertDevice()
    {
        val device = Device("40:F5:20:22:E6:00", "Relay 1", "192.168.0.100", "2",
        0,0,1,0, 0, true, 1)
        val device1 = Device("40:F5:20:22:16:00", "Relay 2", "192.168.0.101", "2",
            0,0,1,0, 0, true, 0)
        val device2 = Device("40:F5:20:22:26:00", "Relay 3", "192.168.0.102", "2",
            0,0,1,0, 0, true, 1)
        val device3 = Device("40:F5:20:22:36:00", "Relay 4", "192.168.0.103", "2",
            0,0,1,0, 0, true, 0)
        viewModelScope.launch {
            deviceRepo.insertDevice(device)
            deviceRepo.insertDevice(device1)
            deviceRepo.insertDevice(device2)
            deviceRepo.insertDevice(device3)
        }
    }

}
