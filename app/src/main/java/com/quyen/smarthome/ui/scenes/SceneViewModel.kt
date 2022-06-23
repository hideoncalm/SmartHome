package com.quyen.smarthome.ui.scenes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quyen.smarthome.base.BaseViewModel
import com.quyen.smarthome.data.model.AlarmTime
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.data.repository.SceneRepository
import com.quyen.smarthome.data.repository.TimeRepository
import com.quyen.smarthome.ui.scenes.adapter.Scene
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SceneViewModel @Inject constructor(
    private val sceneRepo: SceneRepository,
    private val timeRepo: TimeRepository
) : BaseViewModel() {

    val scenes: LiveData<List<Scene>> = sceneRepo.getScenes()

    var alarmsOfScene: MutableLiveData<MutableList<AlarmTime>> = MutableLiveData()

    fun getAlarmsOfScene(scene: Scene) {
        viewModelScope.launch(Dispatchers.IO) {
            val alarms =
                timeRepo.getAlarmDeviceByHourAndMinute(
                    scene.deviceId,
                    scene.hour,
                    scene.minute
                ) as MutableList<AlarmTime>
            alarmsOfScene.postValue(alarms)
        }
    }

    fun deleteScene(alarmTime: AlarmTime) {
        viewModelScope.launch(Dispatchers.IO) {
            timeRepo.deleteAlarmTime(alarmTime)
        }
    }
}
