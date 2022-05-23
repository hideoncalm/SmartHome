package com.quyen.smarthome.data.source.remote

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.quyen.smarthome.data.RoomDataSource
import com.quyen.smarthome.data.model.Room
import com.quyen.smarthome.utils.Constant
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RoomRemoteDataSource @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) : RoomDataSource.Remote {

    private val roomReference = firebaseDatabase.getReference(Constant.ROOM_PATH)
    private val rooms: MutableList<Room> = mutableListOf()

    override suspend fun insertRoom(room: Room): Boolean {
        return roomReference.child(room.room_id).setValue(room).isSuccessful
    }

    override suspend fun updateRoom(room: Room): Boolean {
        return roomReference.child(room.room_id).setValue(room).isSuccessful
    }

    override suspend fun deleteRoom(room: Room): Boolean {
        return roomReference.child(room.room_id).removeValue().isSuccessful
    }

    override suspend fun getRoom(roomId: String): Room {
        val snapshot = roomReference.get().await()
        for (snap: DataSnapshot in snapshot.children) {
            val room: Room? = snap.getValue(Room::class.java)
            room?.let {
                rooms.add(room)
            }
        }
        return rooms[0]
    }

    override suspend fun getRooms(): List<Room> {
        val snapshot = roomReference.get().await()
        for (snap: DataSnapshot in snapshot.children) {
            val room: Room? = snap.getValue(Room::class.java)
            room?.let {
                rooms.add(room)
            }
        }
        return rooms
    }

}
