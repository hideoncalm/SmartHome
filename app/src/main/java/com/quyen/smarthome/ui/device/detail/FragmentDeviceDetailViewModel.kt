package com.quyen.smarthome.ui.device.detail

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.quyen.smarthome.base.BaseViewModel
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.data.model.DeviceTime
import com.quyen.smarthome.data.repository.DeviceRepository
import com.quyen.smarthome.data.repository.TimeRepository
import com.quyen.smarthome.data.source.remote.util.APIService
import com.quyen.smarthome.service.publishMessageMqtt
import com.quyen.smarthome.service.subscribeMqtt
import com.quyen.smarthome.utils.Constant.DEVICE_INFO
import com.quyen.smarthome.utils.Constant.QOS
import com.quyen.smarthome.utils.Constant.STATE_OFF
import com.quyen.smarthome.utils.Constant.STATE_ON
import com.quyen.smarthome.utils.Constant.TURN_OFF_MESSAGE
import com.quyen.smarthome.utils.Constant.TURN_ON_MESSAGE
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
    private val mqttClient: MqttAndroidClient,
    private val timeRepo: TimeRepository,
    private val deviceRepo : DeviceRepository
) : BaseViewModel() {

    private val _isOn : MutableLiveData<Boolean> = MutableLiveData()
    val isOn : LiveData<Boolean>
        get() = _isOn

    private val _dev : MutableLiveData<Device> = MutableLiveData()
    val dev : LiveData<Device>
        get() = _dev

    // time on/off history
    private val times = mutableListOf<DeviceTime>()
    var useTimes: LiveData<List<DeviceTime>> = MutableLiveData()

    // public and subscribe
    private var pushTopic = ""
    private var receiveTopic = ""

    // timer set
    private var _countTime: MutableLiveData<Double> = MutableLiveData()
    val countTime: LiveData<Double>
        get() = _countTime

    // timer countdown
    private var _currentTimeCount: MutableLiveData<Double> = MutableLiveData()
    val currentTimeCount: LiveData<Double>
        get() = _currentTimeCount

    fun turnDeviceOn(device: Device) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // push message on to topic
                pushTopic = device.device_id
                publishMessageMqtt(mqttClient, pushTopic, TURN_ON_MESSAGE, false)
            } catch (e: Exception) {
                Timber.d(e)
            }
        }
    }

    fun turnDeviceOff(device: Device) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // push message off to topic
                pushTopic = device.device_id
                publishMessageMqtt(mqttClient, pushTopic, TURN_OFF_MESSAGE, false)
            } catch (e: Exception) {
                Timber.d(e)
            }
        }
    }

    fun turnDeviceOnOff()
    {
        viewModelScope.launch(Dispatchers.IO) {
            _dev.value?.let {
                if (it.device_info.lowercase().equals("on")) {
                    turnDeviceOff(it)
                } else {
                    turnDeviceOn(it)
                }
            }
        }
    }

    fun subscribeMqttDevice(device: Device) {
        viewModelScope.launch(Dispatchers.IO)  {
            receiveTopic = device.device_id + DEVICE_INFO
            Timber.d("LNQ $receiveTopic")
            subscribeMqtt(mqttClient, receiveTopic, QOS, ::onMessageReceived)
        }
    }

    fun setCountTime(time: Double) {
        _countTime.postValue(time)
    }

    private fun onMessageReceived(topic: String?, message: MqttMessage?) {
        if (topic == receiveTopic) {
            when (message.toString().uppercase()) {
                TURN_ON_MESSAGE -> {
                    val time = getTimeFormat()
                    val deviceTime = DeviceTime(time, pushTopic, STATE_ON)
                    _dev.value!!.device_info = "on"
                    _isOn.postValue(true)
                    viewModelScope.launch(Dispatchers.IO)  {
                        deviceRepo.insertDevice(_dev.value!!)
                    }
                    insertDeviceTime(deviceTime)

                }
                TURN_OFF_MESSAGE -> {
                    val time = getTimeFormat()
                    val deviceTime = DeviceTime(time, pushTopic, STATE_OFF)
                    _dev.value!!.device_info = "off"
                    _isOn.postValue(false)
                    viewModelScope.launch(Dispatchers.IO)  {
                        deviceRepo.insertDevice(_dev.value!!)
                    }
                    insertDeviceTime(deviceTime)
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

    fun getDeviceTimeById(deviceId: String) {
        pushTopic = deviceId
        useTimes = timeRepo.getDeviceTimesById(deviceId)
    }

    private fun insertDeviceTime(deviceTime: DeviceTime) {
        viewModelScope.launch(Dispatchers.IO) {
            timeRepo.insertDeviceTime(deviceTime)
        }
    }

    fun updateDevice(device : Device){
        _dev.postValue(device)
    }
}
