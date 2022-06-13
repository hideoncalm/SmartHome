package com.quyen.smarthome.ui.scenes

import android.transition.Scene
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quyen.smarthome.data.model.AlarmTime
import com.quyen.smarthome.data.repository.DeviceRepository
import com.quyen.smarthome.data.repository.TimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SceneViewModel @Inject constructor(
    private val deviceRepo: DeviceRepository,
    private val timeRepo: TimeRepository
) : ViewModel() {

    private val _scenes: MutableLiveData<MutableList<Scene>> = MutableLiveData()
    val scenes: LiveData<MutableList<Scene>>
        get() = _scenes


    var alarms: MutableLiveData<List<AlarmTime>> = MutableLiveData()

    init {
        val alarm = listOf<AlarmTime>()
        alarms.postValue(alarm)
    }
}
