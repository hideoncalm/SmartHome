package com.quyen.smarthome.ui.device.alarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentAddAlarmViewModel @Inject constructor() : ViewModel() {

    private val _result : MutableLiveData<Boolean> = MutableLiveData()
    val result : LiveData<Boolean>
        get() = _result

    fun insertAlarm()
    {
        viewModelScope.launch(Dispatchers.IO) {
            _result.postValue(true)
        }
    }

    fun deleteAlarm()
    {
        viewModelScope.launch(Dispatchers.IO) {
            _result.postValue(true)
        }
    }

    fun updateAlarm()
    {
        viewModelScope.launch(Dispatchers.IO) {
            _result.postValue(true)
        }
    }
}
