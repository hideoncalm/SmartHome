package com.quyen.smarthome.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    fun showLoading() {
        _isLoading.postValue(true)
    }

    fun hideLoading() {
        _isLoading.postValue(false)
    }

    fun setMessage(ms: String) {
        _message.postValue(ms)
    }
}