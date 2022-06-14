package com.quyen.smarthome.data.repository

import androidx.lifecycle.LiveData
import com.quyen.smarthome.data.model.Room

interface RoomRepository {
    suspend fun insertLocalRoom(room: Room)
    suspend fun updateLocalRoom(room: Room)
    suspend fun deleteLocalRoom(room: Room)
    fun getLocalRooms(): LiveData<List<Room>>
    suspend fun getLocalRoomById(roomId: String): Room?

    suspend fun getRooms() : List<Room>
    suspend fun insertRoom(room: Room) : Boolean
    suspend fun updateRoom(room: Room) : Boolean
    suspend fun deleteRoom(room: Room) : Boolean
    suspend fun getRoom(roomId: String) : Room
}