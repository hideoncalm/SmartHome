package com.quyen.smarthome.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.quyen.smarthome.base.BaseViewModel
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.data.model.Room
import com.quyen.smarthome.data.repository.DeviceRepository
import com.quyen.smarthome.data.repository.RoomRepository
import com.quyen.smarthome.service.subscribeMqtt
import com.quyen.smarthome.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.MqttMessage
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val deviceRepo: DeviceRepository,
    private val roomRepo: RoomRepository,
    private val mqttClient: MqttAndroidClient
) : BaseViewModel() {

    val devices: LiveData<List<Device>> = deviceRepo.getLocalFavoriteDevices()
    val rooms: LiveData<List<Room>> = roomRepo.getLocalRooms()

    private val _hum : MutableLiveData<String> = MutableLiveData()
    val hum : LiveData<String>
        get() = _hum

    private val _temperature : MutableLiveData<String> = MutableLiveData()
    val temperature : LiveData<String>
        get() = _temperature

    private val _power : MutableLiveData<String> = MutableLiveData()
    val power : LiveData<String>
        get() = _power

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

    fun subscribeMqttTemperature() {
        viewModelScope.launch(Dispatchers.IO)  {
            subscribeMqtt(mqttClient, TOPIC_TEMPERATURE, Constant.QOS, ::onMessageReceived)
        }
    }

    fun subscribeMqttHumidity() {
        viewModelScope.launch(Dispatchers.IO)  {
            subscribeMqtt(mqttClient, TOPIC_HUMIDITY, Constant.QOS, ::onMessageReceived)
        }
    }

    fun subscribeMqttPower() {
        viewModelScope.launch(Dispatchers.IO)  {
            subscribeMqtt(mqttClient, TOPIC_POWER, Constant.QOS, ::onMessageReceived)
        }
    }

    private fun onMessageReceived(topic: String?, message: MqttMessage?) {
        when(topic)
        {
            TOPIC_TEMPERATURE -> {
                message?.let {
                    _temperature.postValue(it.toString())
                }
            }
            TOPIC_HUMIDITY -> {
                message?.let {
                    _hum.postValue(it.toString())
                }
            }
            TOPIC_POWER -> {
                message?.let {
                    _power.postValue(it.toString())
                }
            }
            else -> {
            }
        }
    }
    companion object {
        const val TOPIC_TEMPERATURE = "device_info/temperature"
        const val TOPIC_HUMIDITY = "device_info/humidity"
        const val TOPIC_POWER = "device_info/power"
    }
}

