package com.quyen.smarthome.data.repository.imp

import androidx.lifecycle.LiveData
import com.quyen.smarthome.data.model.AlarmTime
import com.quyen.smarthome.data.model.DeviceTime
import com.quyen.smarthome.data.repository.TimeRepository
import com.quyen.smarthome.data.source.local.TimeDao
import javax.inject.Inject

class TimeRepositoryImp @Inject constructor(
    private val timeDao: TimeDao
) : TimeRepository {

    override fun getDeviceTimesById(deviceId: String): LiveData<List<DeviceTime>> =
        timeDao.getDeviceTimesById(deviceId)

    override fun getDeviceTimes(): LiveData<List<DeviceTime>> = timeDao.getDeviceTimes()

    override suspend fun insertDeviceTime(deviceTime: DeviceTime) =
        timeDao.insertDeviceTime(deviceTime)

    override suspend fun updateDeviceTime(deviceTime: DeviceTime) =
        timeDao.updateDeviceTime(deviceTime)

    override suspend fun deleteDeviceTime(deviceTime: DeviceTime) =
        timeDao.deleteDeviceTime(deviceTime)

    override fun getAlarms(): LiveData<List<AlarmTime>> = timeDao.getAlarms()

    override suspend fun getAlarmDeviceByHourAndMinute(
        deviceId: String,
        hour: Int,
        minute: Int
    ): List<AlarmTime> = timeDao.getAlarmDeviceByHourAndMinute(deviceId, hour, minute)

    override suspend fun insertAlarm(alarmTime: AlarmTime) = timeDao.insertAlarm(alarmTime)

    override suspend fun deleteAlarmTime(alarmTime: AlarmTime) = timeDao.deleteAlarmTime(alarmTime)

    override suspend fun updateAlarmTime(alarmTime: AlarmTime) = timeDao.updateAlarmTime(alarmTime)
}
