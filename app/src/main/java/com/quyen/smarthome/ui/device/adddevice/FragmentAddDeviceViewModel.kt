package com.quyen.smarthome.ui.device.adddevice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.URL
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

@HiltViewModel
class FragmentAddDeviceViewModel @Inject constructor() : ViewModel() {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading : LiveData<Boolean>
        get() = _loading

    fun registerDevice(wifiSSID: String, password : String) {
        viewModelScope.launch {
//            val url: URL = URL(ESP_IP_CONFIG)
//            val conn: HttpsURLConnection = url.openConnection() as HttpsURLConnection
//            conn.readTimeout = 100000
//            conn.connectTimeout = 10000
//            conn.requestMethod = "POST"
//            conn.doInput = true
//            conn.doOutput = true
//
//            conn.connect()
        }
    }


}