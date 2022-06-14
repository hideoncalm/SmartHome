package com.quyen.smarthome.ui.scenes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quyen.smarthome.data.model.AlarmTime
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
) : ViewModel() {

    val scenes: LiveData<List<Scene>> = sceneRepo.getScenes()

    var alarmsOfScene: MutableList<AlarmTime> = mutableListOf()

    fun getAlarmsOfScene(scene: Scene) {
        viewModelScope.launch(Dispatchers.IO) {
            alarmsOfScene =
                timeRepo.getAlarmDeviceByHourAndMinute(scene.deviceId, scene.hour, scene.minute) as MutableList<AlarmTime>
        }
    }

    fun deleteScene()
    {
        viewModelScope.launch(Dispatchers.IO) {
            for(alarm in alarmsOfScene) {
                timeRepo.deleteAlarmTime(alarm)
            }
        }
    }

}
