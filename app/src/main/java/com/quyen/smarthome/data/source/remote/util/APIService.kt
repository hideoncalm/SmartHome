package com.quyen.smarthome.data.source.remote.util

import com.quyen.smarthome.data.source.remote.util.APIConfig.PARAM_PASSWORD
import com.quyen.smarthome.data.source.remote.util.APIConfig.PARAM_SSID
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {

    @FormUrlEncoded
    @GET("wifisave")
    suspend fun connectEspToWifi(
        @Field(PARAM_SSID) wifiSSID: String,
        @Field(PARAM_PASSWORD) password: String
    ): Response<String>
}
