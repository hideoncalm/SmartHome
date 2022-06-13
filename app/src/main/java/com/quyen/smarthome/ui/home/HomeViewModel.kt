package com.quyen.smarthome.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.data.model.Room
import com.quyen.smarthome.data.repository.DeviceRepository
import com.quyen.smarthome.data.source.remote.RoomRemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val deviceRepo: DeviceRepository,
    private val roomRepo: RoomRemoteDataSource,
) : ViewModel() {

    val devices: LiveData<List<Device>> = deviceRepo.getLocalFavoriteDevices()

    private val _rooms = MutableLiveData<MutableList<Room>>()
    val rooms: LiveData<MutableList<Room>>
        get() = _rooms

    init {
        getDevices()
        getRooms()
    }

    private fun getDevices() {
        viewModelScope.launch {
            val devices = deviceRepo.getDevices() as MutableList<Device>
            devices.let {
                for (device in it) {
                    deviceRepo.insertLocalDevice(device)
                }
            }
        }
    }

    private fun getRooms() {
        viewModelScope.launch {
            _rooms.postValue(roomRepo.getRooms() as MutableList<Room>?)
        }
    }

}
