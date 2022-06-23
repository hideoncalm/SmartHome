package com.quyen.smarthome.ui.createhouse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quyen.smarthome.base.BaseViewModel
import com.quyen.smarthome.data.model.Home
import com.quyen.smarthome.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentCreateHomeViewModel @Inject constructor(
    private val homeRepo: HomeRepository
) : BaseViewModel() {

    fun createHome(home: Home) {
        viewModelScope.launch(Dispatchers.IO) {
            homeRepo.insertHome(home)
            homeRepo.insertLocalHome(home)
        }
    }

}
