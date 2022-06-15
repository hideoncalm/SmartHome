package com.quyen.smarthome.data.repository

import androidx.lifecycle.LiveData
import com.quyen.smarthome.data.model.AlarmTime
import com.quyen.smarthome.data.model.DeviceTime

interface TimeRepository {

    fun getDeviceTimesById(deviceId: String): LiveData<List<DeviceTime>>
    fun getDeviceTimes(): LiveData<List<DeviceTime>>
    suspend fun insertDeviceTime(deviceTime: DeviceTime)
    suspend fun updateDeviceTime(deviceTime: DeviceTime)
    suspend fun deleteDeviceTime(deviceTime: DeviceTime)

    suspend fun getAlarms(): List<AlarmTime>
    suspend fun getAlarmDeviceByHourAndMinute(deviceId : String, hour: Int, minute: Int) : List<AlarmTime>
    suspend fun insertAlarm(alarmTime: AlarmTime)
    suspend fun deleteAlarmTime(alarmTime: AlarmTime)
    suspend fun updateAlarmTime(alarmTime: AlarmTime)
}
