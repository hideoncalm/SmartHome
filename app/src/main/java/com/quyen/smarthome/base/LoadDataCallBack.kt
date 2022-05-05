package com.quyen.smarthome.base

interface LoadDataCallBack<T> {
    fun onSuccess(data: T)
    fun onFailed(err: String?)
}