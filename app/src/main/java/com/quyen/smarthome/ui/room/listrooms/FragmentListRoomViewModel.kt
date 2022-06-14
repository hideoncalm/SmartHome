package com.quyen.smarthome.ui.room.listrooms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quyen.smarthome.data.model.Room
import com.quyen.smarthome.data.repository.RoomRepository
import com.quyen.smarthome.data.source.remote.RoomRemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentListRoomViewModel @Inject constructor(
    private val roomRepo : RoomRepository
) : ViewModel(){

    val rooms: LiveData<List<Room>> = roomRepo.getLocalRooms()

    fun insertRoom(room : Room)
    {
        viewModelScope.launch {
            roomRepo.insertRoom(room)
        }
    }
}
