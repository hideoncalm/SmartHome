package com.quyen.smarthome.ui.room.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quyen.smarthome.base.BaseViewModel
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.data.model.DeviceTime
import com.quyen.smarthome.data.model.Room
import com.quyen.smarthome.data.repository.DeviceRepository
import com.quyen.smarthome.data.repository.RoomRepository
import com.quyen.smarthome.data.repository.TimeRepository
import com.quyen.smarthome.data.source.remote.util.APIService
import com.quyen.smarthome.service.publishMessageMqtt
import com.quyen.smarthome.service.subscribeMqtt
import com.quyen.smarthome.utils.Constant
import com.quyen.smarthome.utils.getTimeFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.MqttMessage
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RoomDetailViewModel @Inject constructor(
    private val espService: APIService,
    private val mqttClient: MqttAndroidClient,
    private val timeRepo: TimeRepository,
    private val deviceRepository: DeviceRepository,
    private val roomRepository: RoomRepository
) : BaseViewModel() {

    private val _devices: MutableLiveData<MutableList<Device>> = MutableLiveData()
    val devices: LiveData<MutableList<Device>>
        get() = _devices

    lateinit var _room : Room
    private val _isRoomOn : MutableLiveData<Boolean> = MutableLiveData()
    val isRoomOn : LiveData<Boolean>
        get() = _isRoomOn

    private var receiveTopic = ""

    fun getDevicesOffRoom(room: Room) {
        viewModelScope.launch(Dispatchers.IO) {
            _room = room
            _isRoomOn.postValue(room.room_all_device)
            val devices = deviceRepository.getLocalDeviceByRoomID(room.room_id)
            _devices.postValue(devices as MutableList<Device>)
        }
    }

    fun turnAllDeviceOfRoomOff() {
        _devices.value?.let {
            for (device in it) {
                turnDeviceOff(device)
            }
        }
    }

    fun turnAllDeviceOffRoomOn() {
        _devices.value?.let {
            for (device in it) {
                turnDeviceOn(device)
            }
        }
    }

    private fun turnDeviceOn(device: Device) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // push message on to topic
                val pushTopic = device.device_id
                publishMessageMqtt(
                    mqttClient,
                    pushTopic,
                    Constant.TURN_ON_MESSAGE,
                    true
                )
                // get to local server
                val url: String = "http://" + device.device_ip_addr + "/on"
                val response = espService.turnDeviceOn(url)
                if (response.isSuccessful) {
//                    updateDevice(device, Constant.ON)
                }
            } catch (e: Exception) {
                Timber.d(e)
            }
        }
    }

    private fun turnDeviceOff(device: Device) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // push message off to topic
                val pushTopic = device.device_id
                publishMessageMqtt(
                    mqttClient, pushTopic,
                    Constant.TURN_OFF_MESSAGE, true
                )
                // get to local server
                val url: String = "http://" + device.device_ip_addr + "/off"
                val response = espService.turnDeviceOn(url)
                if (response.isSuccessful) {
//                    updateDevice(device, Constant.OFF)
                }
            } catch (e: Exception) {
                Timber.d(e)
            }
        }
    }

    fun subscribeMqttDevice(room: Room) {
        viewModelScope.launch {
            receiveTopic = room.room_id + Constant.ROOM_ALL_DEVICE
            subscribeMqtt(
                mqttClient,
                receiveTopic,
                Constant.QOS,
                ::onMessageReceived
            )
        }
    }

    private fun onMessageReceived(topic: String?, message: MqttMessage?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (topic == receiveTopic) {
                val info = when (message.toString().uppercase()) {
                    Constant.TURN_ON_MESSAGE -> {
                        Constant.ON
                    }
                    Constant.TURN_OFF_MESSAGE -> {
                        Constant.OFF
                    }
                    else -> ""
                }
                updateRoom(_room, info == Constant.ON)
                _devices.value?.let {
                    for (device in it) {
                        updateDevice(device, info)
                    }
                }
            }
        }
    }

    private fun insertDeviceTime(deviceTime: DeviceTime) {
        viewModelScope.launch(Dispatchers.IO) {
            timeRepo.insertDeviceTime(deviceTime)
        }
    }

    private suspend fun updateDevice(device: Device, info: String) {
        val dv = device.copy()
        dv.device_info = info
        deviceRepository.updateDevice(dv)
        deviceRepository.updateLocalDevice(dv)
        val time = getTimeFormat()
        val deviceTime = if (info == Constant.OFF) {
            DeviceTime(time, dv.device_id, Constant.STATE_OFF)
        } else {
            DeviceTime(time, dv.device_id, Constant.STATE_ON)
        }
        insertDeviceTime(deviceTime)
    }

    private fun updateRoom(room : Room, roomAllDeviceState : Boolean)
    {
        viewModelScope.launch {
            _isRoomOn.postValue(roomAllDeviceState)
            val r = room.copy()
            r.room_all_device = roomAllDeviceState
            roomRepository.updateRoom(r)
            roomRepository.updateLocalRoom(r)
        }
    }
}
