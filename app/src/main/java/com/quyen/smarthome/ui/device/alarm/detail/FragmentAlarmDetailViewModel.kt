package com.quyen.smarthome.ui.device.alarm.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quyen.smarthome.base.BaseViewModel
import com.quyen.smarthome.data.model.AlarmTime
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.data.repository.DeviceRepository
import com.quyen.smarthome.data.repository.TimeRepository
import com.quyen.smarthome.ui.scenes.adapter.Scene
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentAlarmDetailViewModel @Inject constructor(
    private val timeRepository: TimeRepository,
    private val deviceRepo : DeviceRepository
) : BaseViewModel() {


    private val _alarmsOfScene: MutableLiveData<MutableList<AlarmTime>> = MutableLiveData()
    val alarmsOfScene: LiveData<MutableList<AlarmTime>>
        get() = _alarmsOfScene

    var device : Device? = null

    fun getDeviceById(deviceId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            device = deviceRepo.getLocalDeviceByID(deviceId)
        }
    }

    fun getAlarmsOfScene(scene: Scene) {
        viewModelScope.launch(Dispatchers.IO) {
            val alarms =
                timeRepository.getAlarmDeviceByHourAndMinute(
                    scene.deviceId,
                    scene.hour,
                    scene.minute
                ) as MutableList<AlarmTime>
            _alarmsOfScene.postValue(alarms)
        }
    }

    fun deleteAlarm(alarmTime: AlarmTime) {
        viewModelScope.launch(Dispatchers.IO) {
            timeRepository.deleteAlarmTime(alarmTime)
        }
    }
}
