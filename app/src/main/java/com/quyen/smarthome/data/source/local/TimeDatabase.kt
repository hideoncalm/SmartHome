package com.quyen.smarthome.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.quyen.smarthome.data.model.AlarmTime
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.data.model.DeviceTime
import com.quyen.smarthome.data.model.Room

@Database(
    entities = [DeviceTime::class, AlarmTime::class, Device::class, Room::class],
    version = 2
)
abstract class TimeDatabase : RoomDatabase() {
    abstract fun getTimeDao(): TimeDao
}
