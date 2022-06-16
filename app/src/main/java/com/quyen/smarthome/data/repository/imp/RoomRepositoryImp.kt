package com.quyen.smarthome.data.repository.imp

import androidx.lifecycle.LiveData
import com.quyen.smarthome.data.RoomDataSource
import com.quyen.smarthome.data.model.Room
import com.quyen.smarthome.data.repository.RoomRepository
import javax.inject.Inject

class RoomRepositoryImp @Inject constructor(
    private val remote : RoomDataSource.Remote,
    private val local : RoomDataSource.Local
) : RoomRepository{

    override suspend fun insertLocalRoom(room: Room) = local.insertRoom(room)

    override suspend fun updateLocalRoom(room: Room) = local.updateRoom(room)

    override suspend fun deleteLocalRoom(room: Room) = local.deleteRoom(room)

    override fun getLocalRooms(): LiveData<List<Room>> = local.getRooms()

    override suspend fun getLocalRoomById(roomId: String): Room? = local.getRoomById(roomId)

    override suspend fun getRoomsByHomeId(homeId: String): List<Room> = local.getRoomsByHomeId(homeId)

    override suspend fun getRooms(): List<Room> = remote.getRooms()

    override suspend fun insertRoom(room: Room): Boolean = remote.insertRoom(room)

    override suspend fun updateRoom(room: Room): Boolean = remote.updateRoom(room)

    override suspend fun deleteRoom(room: Room): Boolean = remote.deleteRoom(room)

    override suspend fun getRoom(roomId: String): Room = remote.getRoom(roomId)
}