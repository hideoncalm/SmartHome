package com.quyen.smarthome.ui.device.adddevice

import android.widget.Toast
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
import retrofit2.Response
import timber.log.Timber
import java.io.BufferedWriter
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.UnsupportedEncodingException
import java.lang.Exception
import java.lang.StringBuilder
import java.net.URL
import java.net.URLEncoder
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

@HiltViewModel
class FragmentAddDeviceViewModel @Inject constructor(
    private val espService : APIService,
    private val firebase : FirebaseDatabase
) : ViewModel() {

    private val deviceRefer = firebase.getReference(Constant.DEVICE_PATH)

    private val _loading : MutableLiveData<Boolean> = MutableLiveData()
    val loading : LiveData<Boolean>
        get() = _loading

    private val _device : MutableLiveData<Device> = MutableLiveData()
    val device : LiveData<Device>
        get() = _device

    val wifiBSSID : MutableLiveData<String> = MutableLiveData()

    fun connectToWfi(wifiSSID: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
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

    private val childEventListener = object : ChildEventListener{
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val device : Device? = snapshot.getValue(Device::class.java)
            device?.let {
                if(it.device_id == wifiBSSID.value)
                {
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

    companion object
    {
//        http://192.168.4.1/wifisave?s=quyenHaHa&p=0966733413
        private const val ESP_URL = "http://192.168.4.1/"
        private const val DEVICE_TYPE_SWITCH = 0
        private const val DEVICE_TYPE_TEMHUM = 0
    }

}
