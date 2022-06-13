package com.quyen.smarthome.data.repository

import androidx.lifecycle.LiveData
import com.quyen.smarthome.data.model.Device

interface DeviceRepository {
    /*
        Function local
     */
    fun getLocalDevices(): LiveData<List<Device>>
    fun getLocalFavoriteDevices(): LiveData<List<Device>>
    fun getLocalDeviceByRoomID(roomId: String): LiveData<List<Device>>
    suspend fun getLocalDeviceByID(deviceID: String): Device?
    suspend fun insertLocalDevice(device: Device)
    suspend fun deleteLocalDevice(device: Device)
    suspend fun updateLocalDevice(device: Device)

    /*
        Remote function
     */
    suspend fun insertDevice(device: Device): Boolean
    suspend fun deleteDevice(deviceId: String): Boolean
    suspend fun updateDevice(device: Device): Boolean
    suspend fun getDevices(): List<Device>?

}
