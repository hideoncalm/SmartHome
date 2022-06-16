package com.quyen.smarthome.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.quyen.smarthome.data.model.*

@Database(
    entities = [DeviceTime::class, AlarmTime::class, Device::class, Room::class, Home::class],
    version = 4
)
abstract class TimeDatabase : RoomDatabase() {
    abstract fun getTimeDao(): TimeDao
}
