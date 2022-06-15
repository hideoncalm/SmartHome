package com.quyen.smarthome.data.repository.imp

import androidx.lifecycle.LiveData
import com.quyen.smarthome.data.DeviceDataSource
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.data.repository.DeviceRepository
import javax.inject.Inject

class DeviceRepositoryImp @Inject constructor(
    private val local: DeviceDataSource.Local,
    private val remote: DeviceDataSource.Remote
) : DeviceRepository {

    override fun getLocalDevices(): LiveData<List<Device>> = local.getDevices()

    override fun getLocalFavoriteDevices(): LiveData<List<Device>> = local.getFavoriteDevices()

    override suspend fun getLocalDeviceByRoomID(roomId: String): List<Device> =
        local.getDevicesByRoomId(roomId)

    override suspend fun getLocalDeviceByID(deviceID: String): Device? =
        local.getDeviceById(deviceID)

    override suspend fun insertLocalDevice(device: Device) = local.insertDevice(device)

    override suspend fun deleteLocalDevice(device: Device) = local.deleteDevice(device)

    override suspend fun updateLocalDevice(device: Device) = local.updateDevice(device)

    override suspend fun insertDevice(device: Device): Boolean = remote.insertDevice(device)

    override suspend fun deleteDevice(deviceId: String): Boolean = remote.deleteDevice(deviceId)

    override suspend fun updateDevice(device: Device): Boolean = remote.updateDevice(device)

    override suspend fun getDevices(): List<Device>? = remote.getDevices()
}
