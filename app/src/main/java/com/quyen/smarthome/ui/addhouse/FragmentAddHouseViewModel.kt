package com.quyen.smarthome.ui.addhouse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quyen.smarthome.data.model.Home
import com.quyen.smarthome.data.source.remote.HomeRemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentAddHouseViewModel @Inject constructor(
    private val homeRepo : HomeRemoteDataSource
) : ViewModel(){

    private val _houses = MutableLiveData<MutableList<Home>>()
    val houses: LiveData<MutableList<Home>>
        get() = _houses

    init {
        getHouses()
    }

    private fun getHouses() {
        viewModelScope.launch {
            _houses.postValue(homeRepo.getHomes() as MutableList<Home>?)
        }
    }

    fun insertHome(home: Home) {
        viewModelScope.launch {
            homeRepo.insertHome(home)
        }
    }
}
