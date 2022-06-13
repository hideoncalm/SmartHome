package com.quyen.smarthome.data

import androidx.lifecycle.LiveData
import com.quyen.smarthome.data.model.Device

interface DeviceDataSource {
    interface Remote {
        suspend fun insertDevice(device: Device): Boolean
        suspend fun deleteDevice(deviceId: String): Boolean
        suspend fun updateDevice(device: Device): Boolean
        suspend fun getDevices(): List<Device>?
    }

    interface Local {
        suspend fun insertDevice(device: Device)
        fun getDevices(): LiveData<List<Device>>
        fun getFavoriteDevices(): LiveData<List<Device>>
        fun getDevicesByRoomId(roomID: String): LiveData<List<Device>>
        suspend fun getDeviceById(deviceID: String): Device?
        suspend fun updateDevice(device: Device)
        suspend fun deleteDevice(device: Device)
    }
}
