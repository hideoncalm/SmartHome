package com.quyen.smarthome.ui.device.listdevices

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.data.source.remote.DeviceRemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListDevicesViewModel @Inject constructor(
    private val deviceDataSource: DeviceRemoteDataSource
) : ViewModel() {

    private val _devices = MutableLiveData<MutableList<Device>>()
    val devices: LiveData<MutableList<Device>>
        get() = _devices

    init {
        getNewDevices()
    }

    private fun getNewDevices(){
        viewModelScope.launch {
            _devices.postValue(deviceDataSource.getDevicesByRoomId(0) as MutableList<Device>?)
        }
    }
}