package com.quyen.smarthome.data.source.local

import androidx.lifecycle.LiveData
import com.quyen.smarthome.data.RoomDataSource
import com.quyen.smarthome.data.model.Room
import javax.inject.Inject

class RoomLocalDataSource @Inject constructor(
    private val timeDao: TimeDao
) : RoomDataSource.Local{

    override fun getRooms(): LiveData<List<Room>> = timeDao.getRooms()

    override suspend fun insertRoom(room: Room) = timeDao.insertRoom(room)

    override suspend fun updateRoom(room: Room) = timeDao.updateRoom(room)

    override suspend fun deleteRoom(room: Room) = timeDao.deleteRoom(room)

    override suspend fun getRoomById(roomId: String) = timeDao.getRoomById(roomId)
}