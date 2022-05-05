package com.quyen.smarthome.data

import com.quyen.smarthome.data.model.DHT11
import com.quyen.smarthome.data.model.Relay

interface DeviceDataSource {
    interface Remote {
        suspend fun insertRelay(relay: Relay): Boolean
        suspend fun deleteRelay(relayId: String): Boolean
        suspend fun updateRelay(relay: Relay): Boolean
        suspend fun getRelaysByRoomId(roomId: String): List<Relay>?

        suspend fun insertDHT11(relay: Relay): Boolean
        suspend fun deleteDHT11(relayId: String): Boolean
        suspend fun updateDHT11(relay: Relay): Boolean
        suspend fun getDHT11ByRoomId(roomId: String): DHT11?
    }
}