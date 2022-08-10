package com.quyen.smarthome.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quyen.smarthome.base.BaseViewModel
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.data.model.Home
import com.quyen.smarthome.data.model.Room
import com.quyen.smarthome.data.repository.DeviceRepository
import com.quyen.smarthome.data.repository.RoomRepository
import com.quyen.smarthome.data.source.remote.util.APIService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.eclipse.paho.android.service.MqttAndroidClient
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val deviceRepo: DeviceRepository,
    private val roomRepo: RoomRepository
) : BaseViewModel() {

    val devices: LiveData<List<Device>> = deviceRepo.getLocalFavoriteDevices()

    val rooms: LiveData<List<Room>> = roomRepo.getLocalRooms()

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
            val rooms = roomRepo.getRooms() as MutableList<Room>
            for (room in rooms) {
                roomRepo.insertLocalRoom(room)
            }
        }
    }

}
