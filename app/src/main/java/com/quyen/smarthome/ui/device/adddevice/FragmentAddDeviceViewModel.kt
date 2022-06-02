package com.quyen.smarthome.ui.device.adddevice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.data.source.remote.util.APIService
import com.quyen.smarthome.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FragmentAddDeviceViewModel @Inject constructor(
    private val espService: APIService,
    private val firebase: FirebaseDatabase
) : ViewModel() {

//        http://192.168.4.1/wifisave?s=quyenHaHa&p=0966733413

    private val deviceRefer = firebase.getReference(Constant.DEVICE_PATH)

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _device: MutableLiveData<Device> = MutableLiveData()
    val device: LiveData<Device>
        get() = _device

    var wifiBSSID: String = ""

    fun connectToWfi(wifiSSID: String, password: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                _loading.postValue(true)
                val result = espService.connectEspToWifi(wifiSSID, password)
                _loading.postValue(result.code() == 200)
            } catch (e: Exception) {
            }
            // listen for database change
            deviceRefer.addChildEventListener(childEventListener)
        }
    }

    private val childEventListener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val device: Device? = snapshot.getValue(Device::class.java)
            device?.let {
                if (wifiBSSID.contains(it.device_id)) {
                    _device.postValue(it)
                    _loading.postValue(false)
                }
            }
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onCancelled(error: DatabaseError) {
        }

    }

}
