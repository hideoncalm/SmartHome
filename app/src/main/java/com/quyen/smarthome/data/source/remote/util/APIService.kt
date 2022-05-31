package com.quyen.smarthome.data.source.remote.util

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIService {

    @FormUrlEncoded
    @POST("config")
    suspend fun connectEspToWifi(
        @Field("wifiSSID") wifiSSID: String,
        @Field("password") password: String
    ): Response<String>
}