package com.quyen.smarthome.ui.device.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.data.source.remote.util.APIService
import com.quyen.smarthome.service.disconnectMqtt
import com.quyen.smarthome.service.mqttClientConnect
import com.quyen.smarthome.service.publishMessageMqtt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttException
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class FragmentDeviceDetailViewModel @Inject constructor(
    private val espService: APIService,
    private val mqttClient: MqttAndroidClient
) : ViewModel() {

/*
    "http://192.168.70.206/1"
    "http://192.168.70.206/0"
*/

    private val _isSucceed: MutableLiveData<Boolean> = MutableLiveData()
    val isSucceed: LiveData<Boolean>
        get() = _isSucceed

    fun turnDeviceOn(device: Device) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // push message on to topic
                val token: IMqttToken = mqttClient.connect()
                token.actionCallback = object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        publishMessageMqtt(mqttClient, device.device_id, TURN_ON_MESSAGE, true)
                    }

                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                        Timber.d("MQTT client : Connected Failed")
                    }
                }

                // get to local server
                val url: String = "http://" + device.device_ip_addr + "/1"
                val response = espService.turnDeviceOn(url)
                if (response.isSuccessful) {
                    _isSucceed.postValue(true)
                } else {
                    _isSucceed.postValue(false)
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
                val token: IMqttToken = mqttClient.connect()
                token.actionCallback = object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        publishMessageMqtt(mqttClient, device.device_id, TURN_OFF_MESSAGE, true)
                    }

                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                        Timber.d("MQTT client : Connected Failed")
                    }
                }
                // get to local server
                val url: String = "http://" + device.device_ip_addr + "/0"
                val response = espService.turnDeviceOn(url)
                if (response.isSuccessful) {
                    _isSucceed.postValue(true)
                } else {
                    _isSucceed.postValue(false)
                }
            } catch (e: Exception) {
                Timber.d(e.message)
            }
        }
    }

    fun connectMqtt()
    {
        mqttClientConnect(mqttClient)
    }

    fun disConnectMqtt() {
        disconnectMqtt(mqttClient)
    }

    companion object {
        private const val TURN_ON_MESSAGE = "ON"
        private const val TURN_OFF_MESSAGE = "OFF"
    }
}
