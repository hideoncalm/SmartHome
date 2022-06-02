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

}
