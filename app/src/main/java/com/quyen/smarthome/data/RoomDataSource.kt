package com.quyen.smarthome.data

import androidx.lifecycle.LiveData
import com.quyen.smarthome.data.model.Room

interface RoomDataSource {
    interface Remote {
        suspend fun getRooms(): List<Room>
        suspend fun insertRoom(room: Room): Boolean
        suspend fun updateRoom(room: Room): Boolean
        suspend fun deleteRoom(room: Room): Boolean
        suspend fun getRoom(roomId: String): Room
    }

    interface Local {
        fun getRooms(): LiveData<List<Room>>
        suspend fun getRoomsByHomeId(homeId: String): List<Room>
        suspend fun insertRoom(room: Room)
        suspend fun updateRoom(room: Room)
        suspend fun deleteRoom(room: Room)
        suspend fun getRoomById(roomId: String): Room?
    }
}
