package com.quyen.smarthome.ui.device.detail

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.data.model.DeviceTime
import com.quyen.smarthome.data.source.remote.util.APIService
import com.quyen.smarthome.service.publishMessageMqtt
import com.quyen.smarthome.service.subscribeMqtt
import com.quyen.smarthome.utils.Constant.STATE_OFF
import com.quyen.smarthome.utils.Constant.STATE_ON
import com.quyen.smarthome.utils.getTimeFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.MqttMessage
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FragmentDeviceDetailViewModel @Inject constructor(
    private val espService: APIService,
    private val mqttClient: MqttAndroidClient
) : ViewModel() {

    // device State
    private val _isOn: MutableLiveData<Boolean> = MutableLiveData()
    val isOn: LiveData<Boolean>
        get() = _isOn

    // time on/off history
    private val times = mutableListOf<DeviceTime>()
    private val _useTimes = MutableLiveData<MutableList<DeviceTime>>()
    val useTimes: LiveData<MutableList<DeviceTime>>
        get() = _useTimes

    // public and subscribe
    private var pushTopic = ""
    private var receiveTopic = ""

    // timer set
    private var _countTime : MutableLiveData<Double> = MutableLiveData()
    val countTime : LiveData<Double>
        get() = _countTime

    // timer countdown
    private var _currentTimeCount : MutableLiveData<Double> = MutableLiveData()
    val currentTimeCount : LiveData<Double>
        get() = _currentTimeCount


    fun turnDeviceOn(device: Device) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // push message on to topic
                pushTopic = device.device_id
                publishMessageMqtt(mqttClient, pushTopic, TURN_ON_MESSAGE, true)
                // get to local server
                val url: String = "http://" + device.device_ip_addr + "/on"
                val response = espService.turnDeviceOn(url)
                if (response.isSuccessful) {
                    _isOn.postValue(true)
                }
            } catch (e: Exception) {
                Timber.d(e.message)
            }
        }
    }

    fun turnDeviceOff(device: Device) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // push message off to topic
                pushTopic = device.device_id
                publishMessageMqtt(mqttClient, pushTopic, TURN_OFF_MESSAGE, true)
                // get to local server
                val url: String = "http://" + device.device_ip_addr + "/off"
                val response = espService.turnDeviceOn(url)
                if (response.isSuccessful) {
                    _isOn.postValue(false)
                }
            } catch (e: Exception) {
                Timber.d(e.message)
            }
        }
    }

    fun subscribeMqttDevice(device: Device) {
        viewModelScope.launch {
            if (device.device_info.uppercase().contains(TURN_ON_MESSAGE)) {
                _isOn.postValue(true)
            } else {
                _isOn.postValue(false)
            }
            receiveTopic = device.device_id + DEVICE_INFO
            subscribeMqtt(mqttClient, receiveTopic, QOS, ::onMessageReceived)
        }
    }

    fun setCountTime(time : Double)
    {
        _countTime.postValue(time)
    }

    private fun onMessageReceived(topic: String?, message: MqttMessage?) {
        if (topic == receiveTopic) {
            when (message.toString().uppercase()) {
                TURN_ON_MESSAGE -> {
                    val time = getTimeFormat()
                    times.add(DeviceTime(time, pushTopic, STATE_ON))
                    _useTimes.postValue(times)
                    _isOn.postValue(true)
                }
                TURN_OFF_MESSAGE -> {
                    val time = getTimeFormat()
                    times.add(DeviceTime(time, pushTopic, STATE_OFF))
                    _useTimes.postValue(times)
                    _isOn.postValue(false)
                }
                else -> {
                }
            }
        }
    }

    fun startCountDownTimer(time: Double) {
        // set Count Down Timer
        val countDownTimer = object : CountDownTimer((time * 1000).toLong(), 1000) {
            override fun onTick(p0: Long) {
                _currentTimeCount.postValue(p0.toDouble())
            }

            override fun onFinish() {
                _currentTimeCount.postValue(0.0)
            }
        }
        countDownTimer.start()
    }

    companion object {
        private const val TURN_ON_MESSAGE = "ON"
        private const val TURN_OFF_MESSAGE = "OFF"
        private const val DEVICE_INFO = "/device_info"
        private const val QOS = 1
    }
}
