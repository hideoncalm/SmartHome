package com.quyen.smarthome.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.quyen.smarthome.data.model.AlarmTime
import com.quyen.smarthome.data.model.DeviceTime

@Database(
    entities = [DeviceTime::class, AlarmTime::class],
    version = 1
)
abstract class TimeDatabase : RoomDatabase() {
    abstract fun getTimeDao(): TimeDao
}