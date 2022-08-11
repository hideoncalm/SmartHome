package com.quyen.smarthome.ui.room.createroom

import androidx.lifecycle.viewModelScope
import com.quyen.smarthome.base.BaseViewModel
import com.quyen.smarthome.data.model.Room
import com.quyen.smarthome.data.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentCreateRoomViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : BaseViewModel() {

    fun insertRoom(room: Room) {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.insertRoom(room)
            roomRepository.insertLocalRoom(room)
        }
    }
}