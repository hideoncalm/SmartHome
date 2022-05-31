package com.quyen.smarthome.ui.device.adddevice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quyen.smarthome.data.source.remote.util.APIService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception
import java.net.URL
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

@HiltViewModel
class FragmentAddDeviceViewModel @Inject constructor(
    private val espService : APIService
) : ViewModel() {

    private val _response: MutableLiveData<Response<String>> = MutableLiveData()
    val response: LiveData<Response<String>>
        get() = _response

    private val _loading : MutableLiveData<Boolean> = MutableLiveData(false)
    val loading : LiveData<Boolean>
        get() = _loading

    fun registerDevice(wifiSSID: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.postValue(true)
                val result = espService.connectEspToWifi(wifiSSID, password)
                _response.postValue(result)
            } catch (e: Exception) {
                _loading.postValue(false)
            }
        }
    }

    companion object
    {
//        http://192.168.4.1/wifisave?s=quyenHaHa&p=0966733413
        private const val ESP_URL = "http://192.168.4.1/"
    }

}
