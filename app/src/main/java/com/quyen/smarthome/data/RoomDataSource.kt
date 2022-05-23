package com.quyen.smarthome.data

import com.quyen.smarthome.data.model.Room

interface RoomDataSource {
    interface Remote {
        suspend fun getRooms() : List<Room>
        suspend fun insertRoom(room: Room) : Boolean
        suspend fun updateRoom(room: Room) : Boolean
        suspend fun deleteRoom(room: Room) : Boolean
        suspend fun getRoom(roomId: String) : Room
    }
}
