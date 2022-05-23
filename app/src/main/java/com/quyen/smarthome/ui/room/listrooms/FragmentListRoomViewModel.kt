package com.quyen.smarthome.ui.room.listrooms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quyen.smarthome.data.model.Room
import com.quyen.smarthome.data.source.remote.RoomRemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentListRoomViewModel @Inject constructor(
    private val roomRepo : RoomRemoteDataSource
) : ViewModel(){

    private val _rooms = MutableLiveData<MutableList<Room>>()
    val rooms: LiveData<MutableList<Room>>
        get() = _rooms

    init {
        getRooms()
    }

    private fun getRooms() {
        viewModelScope.launch {
            _rooms.postValue(roomRepo.getRooms() as MutableList<Room>?)
        }
    }

    fun insertRoom(room : Room)
    {
        viewModelScope.launch {
            roomRepo.insertRoom(room)
        }
    }
}