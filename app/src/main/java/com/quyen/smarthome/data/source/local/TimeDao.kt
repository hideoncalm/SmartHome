package com.quyen.smarthome.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.quyen.smarthome.data.model.AlarmTime
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.data.model.DeviceTime

@Dao
interface TimeDao {

    /*
        user for get device on/off time
     */
    @Query("select * from device_time where device_id like :deviceId")
    fun getDeviceTimesById(deviceId: String): LiveData<List<DeviceTime>>

    @Query("select * from device_time")
    fun getDeviceTimes(): LiveData<List<DeviceTime>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeviceTime(deviceTime: DeviceTime)

    @Update
    suspend fun updateDeviceTime(deviceTime: DeviceTime)

    @Delete
    suspend fun deleteDeviceTime(deviceTime: DeviceTime)


    /*
        use for get alarm time
     */
    @Query("select * from alarm")
    fun getAlarms(): LiveData<List<AlarmTime>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarmTime: AlarmTime)

    @Delete
    suspend fun deleteAlarmTime(alarmTime: AlarmTime)

    @Update
    suspend fun updateAlarmTime(alarmTime: AlarmTime)


    /*
        use for get alarm time
     */

    @Query("select * from device")
    fun getDevices(): LiveData<List<Device>>

    @Query("select * from device where device_favorite = 1")
    fun getFavoriteDevices(): LiveData<List<Device>>

    @Query("select * from device where device_id like :deviceID")
    suspend fun getDeviceByID(deviceID : String): Device?

    @Query("select * from device where device_room_id like :roomId")
    fun getDeviceByRoomID(roomId : String): LiveData<List<Device>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevice(device: Device)

    @Delete
    suspend fun deleteDevice(device: Device)

    @Update
    suspend fun updateDevice(device: Device)
}
