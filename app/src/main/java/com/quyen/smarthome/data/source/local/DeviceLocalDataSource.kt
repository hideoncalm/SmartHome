package com.quyen.smarthome.data.source.local

import androidx.lifecycle.LiveData
import com.quyen.smarthome.data.DeviceDataSource
import com.quyen.smarthome.data.model.Device
import javax.inject.Inject

class DeviceLocalDataSource @Inject constructor(
    private val timeDao: TimeDao
) :  DeviceDataSource.Local{

    override suspend fun insertDevice(device: Device) {
        timeDao.insertDevice(device)
    }

    override fun getDevices(): LiveData<List<Device>> = timeDao.getDevices()

    override fun getFavoriteDevices(): LiveData<List<Device>> = timeDao.getFavoriteDevices()

    override suspend fun getDevicesByRoomId(roomID : String): List<Device> = timeDao.getDeviceByRoomID(roomID)

    override suspend fun getDeviceById(deviceID: String): Device? = timeDao.getDeviceByID(deviceID)

    override suspend fun updateDevice(device: Device) = timeDao.updateDevice(device)

    override suspend fun deleteDevice(device: Device) = timeDao.deleteDevice(device)
}
