package com.quyen.smarthome.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.quyen.smarthome.data.model.AlarmTime
import com.quyen.smarthome.data.model.DeviceTime
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeDao {

    @Query("select * from device_time where device_id = :deviceId")
    fun getDeviceTimesById(deviceId : String) : LiveData<List<DeviceTime>>

    @Query("select * from device_time")
    fun getDeviceTimes() : LiveData<List<DeviceTime>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeviceTime(deviceTime: DeviceTime)

    @Update
    suspend fun updateDeviceTime(deviceTime: DeviceTime)

    @Delete
    suspend fun deleteDeviceTime(deviceTime: DeviceTime)

    @Query("select * from alarm")
    fun getAlarms() : LiveData<List<AlarmTime>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarmTime: AlarmTime)

    @Delete
    suspend fun deleteAlarmTime(alarmTime: AlarmTime)

    @Update
    suspend fun updateAlarmTime(alarmTime: AlarmTime)
}