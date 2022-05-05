package com.quyen.smarthome.data

import com.quyen.smarthome.data.model.Device

interface DeviceDataSource {
    interface Remote {
        suspend fun insertDevice(device: Device): Boolean
        suspend fun deleteDevice(deviceId: String): Boolean
        suspend fun updateDevice(device: Device): Boolean
        suspend fun getDevicesByRoomId(roomId: Int = 0): List<Device>?
    }
}
