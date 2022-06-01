package com.quyen.smarthome.ui.device.addwifi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quyen.smarthome.data.source.remote.util.APIService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class FragmentChooseWifiViewModel @Inject constructor(
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
