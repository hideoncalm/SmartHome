package com.quyen.smarthome.ui.addhouse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quyen.smarthome.data.model.Home
import com.quyen.smarthome.data.repository.HomeRepository
import com.quyen.smarthome.data.source.remote.HomeRemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentAddHouseViewModel @Inject constructor(
    private val homeRepo: HomeRepository
) : ViewModel() {

    val houses: LiveData<List<Home>> = homeRepo.getLocalHomes()

    init {
        getHouses()
    }

    private fun getHouses() {
        viewModelScope.launch {
            val houses = homeRepo.getHomes() as MutableList<Home>
            for (house in houses) {
                homeRepo.insertLocalHome(house)
            }
        }
    }

    fun insertHome(home: Home) {
        viewModelScope.launch {
            homeRepo.insertHome(home)
            homeRepo.insertLocalHome(home)
        }
    }
}
