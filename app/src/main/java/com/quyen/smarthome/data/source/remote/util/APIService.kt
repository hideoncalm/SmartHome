package com.quyen.smarthome.data.source.remote.util

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIService {

    @FormUrlEncoded
    @POST("wifisave")
    suspend fun connectEspToWifi(
        @Field("s") wifiSSID: String,
        @Field("p") password: String
    ): Response<String>
}
