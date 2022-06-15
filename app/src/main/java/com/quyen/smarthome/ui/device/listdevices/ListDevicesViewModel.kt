package com.quyen.smarthome.ui.device.listdevices

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.data.model.DeviceTime
import com.quyen.smarthome.data.repository.DeviceRepository
import com.quyen.smarthome.data.repository.TimeRepository
import com.quyen.smarthome.data.source.remote.util.APIService
import com.quyen.smarthome.service.publishMessageMqtt
import com.quyen.smarthome.service.subscribeMqtt
import com.quyen.smarthome.utils.Constant
import com.quyen.smarthome.utils.Constant.DEVICE_INFO
import com.quyen.smarthome.utils.Constant.OFF
import com.quyen.smarthome.utils.Constant.ON
import com.quyen.smarthome.utils.Constant.QOS
import com.quyen.smarthome.utils.Constant.TOPIC_SPLIT
import com.quyen.smarthome.utils.Constant.TURN_OFF_MESSAGE
import com.quyen.smarthome.utils.Constant.TURN_ON_MESSAGE
import com.quyen.smarthome.utils.getTimeFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.MqttMessage
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ListDevicesViewModel @Inject constructor(
    private val espService: APIService,
    private val mqttClient: MqttAndroidClient,
    private val deviceRepo: DeviceRepository,
    private val timeRepo: TimeRepository
) : ViewModel() {

    val devices: LiveData<List<Device>> = deviceRepo.getLocalDevices()

    // public and subscribe

    fun updateDevice(device: Device) {
        viewModelScope.launch(Dispatchers.IO) {
            deviceRepo.updateLocalDevice(device)
            deviceRepo.updateDevice(device)
        }
    }

    fun turnDeviceOn(device: Device) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // push message on to topic
                val pushTopic = device.device_id
                publishMessageMqtt(
                    mqttClient,
                    pushTopic,
                    TURN_ON_MESSAGE,
                    true
                )
                // get to local server
                val url: String = "http://" + device.device_ip_addr + "/on"
                val response = espService.turnDeviceOn(url)
                if (response.isSuccessful) {
                    updateDevice(device, ON)
                }
            } catch (e: Exception) {
                Timber.d(e)
            }
        }
    }

    fun turnDeviceOff(device: Device) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // push message off to topic
                val pushTopic = device.device_id
                publishMessageMqtt(
                    mqttClient, pushTopic,
                    TURN_OFF_MESSAGE, true
                )
                // get to local server
                val url: String = "http://" + device.device_ip_addr + "/off"
                val response = espService.turnDeviceOn(url)
                if (response.isSuccessful) {
                    updateDevice(device, OFF)
                }
            } catch (e: Exception) {
                Timber.d(e)
            }
        }
    }

    fun subscribeMqttDevice(device: Device) {
        viewModelScope.launch {
            val receiveTopic = device.device_id + DEVICE_INFO
            subscribeMqtt(
                mqttClient,
                receiveTopic,
                QOS,
                ::onMessageReceived
            )
        }
    }

    private fun onMessageReceived(topic: String?, message: MqttMessage?) {
        viewModelScope.launch(Dispatchers.IO) {
            val device = async { getDeviceOfTopic(topic) }
            device.await()?.let {
                when (message.toString().uppercase()) {
                    TURN_ON_MESSAGE -> {
                        val time = getTimeFormat()
                        val deviceTime = DeviceTime(time, it.device_id, Constant.STATE_ON)
                        updateDevice(it, ON)
                        insertDeviceTime(deviceTime)
                    }
                    TURN_OFF_MESSAGE -> {
                        val time = getTimeFormat()
                        val deviceTime = DeviceTime(time, it.device_id, Constant.STATE_OFF)
                        updateDevice(it, OFF)
                        insertDeviceTime(deviceTime)
                    }
                    else -> {
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

    private suspend fun getDeviceOfTopic(topic: String?): Device? {
        var device : Device? = null
        topic?.let {
            val deviceId = topic.substringBefore(TOPIC_SPLIT)
            device = deviceRepo.getLocalDeviceByID(deviceId)
        }
        return device
    }

    private suspend fun updateDevice(device: Device, info : String)
    {
        val dv = device.copy()
        dv.device_info = info
        deviceRepo.updateDevice(dv)
        deviceRepo.updateLocalDevice(dv)
    }
}
