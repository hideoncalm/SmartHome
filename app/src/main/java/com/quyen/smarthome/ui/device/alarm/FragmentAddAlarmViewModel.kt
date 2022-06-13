package com.quyen.smarthome.ui.device.alarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quyen.smarthome.data.model.AlarmTime
import com.quyen.smarthome.data.source.local.TimeDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentAddAlarmViewModel @Inject constructor(
    private val timeDao: TimeDao
) : ViewModel() {

    private val _result : MutableLiveData<Boolean> = MutableLiveData()
    val result : LiveData<Boolean>
        get() = _result

    fun insertAlarm(alarmTime: AlarmTime)
    {
        viewModelScope.launch(Dispatchers.IO) {
            timeDao.deleteAlarmTime(alarmTime)
            _result.postValue(true)
        }
    }

    fun deleteAlarm(alarmTime: AlarmTime)
    {
        viewModelScope.launch(Dispatchers.IO) {
            _result.postValue(true)
        }
    }

    fun updateAlarm(alarmTime: AlarmTime)
    {
        viewModelScope.launch(Dispatchers.IO) {
            _result.postValue(true)
        }
    }
}
