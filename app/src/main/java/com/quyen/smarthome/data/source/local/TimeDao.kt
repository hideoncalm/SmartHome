package com.quyen.smarthome.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.quyen.smarthome.data.model.AlarmTime
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.data.model.DeviceTime
import com.quyen.smarthome.data.model.Room
import com.quyen.smarthome.ui.scenes.adapter.Scene

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

    @Query("select *from alarm where alarm.device_id like :deviceId and alarm.hour = :hour and alarm.minute = :minute")
    suspend fun getAlarmDeviceByHourAndMinute(
        deviceId: String,
        hour: Int,
        minute: Int
    ): List<AlarmTime>

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
    suspend fun getDeviceByID(deviceID: String): Device?

    @Query("select * from device where device_room_id like :roomId")
    fun getDeviceByRoomID(roomId: String): LiveData<List<Device>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevice(device: Device)

    @Delete
    suspend fun deleteDevice(device: Device)

    @Update
    suspend fun updateDevice(device: Device)


    /*
        room
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoom(room: Room)

    @Update
    suspend fun updateRoom(room: Room)

    @Delete
    suspend fun deleteRoom(room: Room)

    @Query("select * from room")
    fun getRooms(): LiveData<List<Room>>

    @Query("select * from room where room_id like :roomId")
    suspend fun getRoomById(roomId: String): Room?

    /*
        scenes
     */

    @Query(
        "select device.device_id, device.device_name, alarm.hour, alarm.minute, room.room_name, room.room_image " +
                "from device, alarm, room " +
                "where device.device_id = alarm.device_id and device.device_room_id = room.room_id " +
                "group by hour, minute"
    )
    fun getScenes(): LiveData<List<Scene>>
}
