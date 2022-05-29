package com.quyen.smarthome.ui.device.adddevice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class FragmentAddDeviceViewModel @Inject constructor() : ViewModel() {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading : LiveData<Boolean>
        get() = _loading


    fun registerDevice(url : URL, wifiSSID: String, password : String)
    {

    }


}