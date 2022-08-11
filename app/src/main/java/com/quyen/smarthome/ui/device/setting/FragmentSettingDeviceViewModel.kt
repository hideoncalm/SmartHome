package com.quyen.smarthome.ui.device.setting

import androidx.lifecycle.viewModelScope
import com.quyen.smarthome.base.BaseViewModel
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.data.repository.DeviceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentSettingDeviceViewModel @Inject constructor(
    private val deviceRepo: DeviceRepository
) : BaseViewModel() {

    fun updateDevice(device: Device) {
        viewModelScope.launch(Dispatchers.IO)
        {
            deviceRepo.updateDevice(device)
            deviceRepo.updateLocalDevice(device)
        }
    }
}