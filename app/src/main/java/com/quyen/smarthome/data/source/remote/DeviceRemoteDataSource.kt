package com.quyen.smarthome.data.source.remote

import com.google.firebase.database.*
import com.quyen.smarthome.data.DeviceDataSource
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.utils.Constant.DEVICE_PATH
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class DeviceRemoteDataSource @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) : DeviceDataSource.Remote {

    private var deviceReference : DatabaseReference = firebaseDatabase.getReference(DEVICE_PATH)
    private val devices = mutableListOf<Device>()

    override suspend fun insertDevice(device: Device): Boolean {
        return deviceReference.child(device.device_id).setValue(device).isSuccessful
    }

    override suspend fun deleteDevice(deviceId: String): Boolean {
        return deviceReference.child(deviceId).removeValue().isSuccessful
    }

    override suspend fun updateDevice(device: Device): Boolean {
        return deviceReference.child(device.device_id).setValue(device).isSuccessful
    }

    override suspend fun getDevicesByRoomId(roomId: Int): List<Device>? {
        val snapshot = deviceReference.get().await()
        for(snap : DataSnapshot in snapshot.children)
        {
            val device : Device? = snap.getValue(Device::class.java)
            device?.let {
                devices.add(device)
            }
        }
        return devices
    }

}
