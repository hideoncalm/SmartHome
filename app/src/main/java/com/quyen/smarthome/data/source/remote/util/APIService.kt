package com.quyen.smarthome.data.source.remote.util

import retrofit2.Response
import retrofit2.http.*

interface APIService {

    @FormUrlEncoded
    @POST("wifisave")
    suspend fun connectEspToWifi(
        @Field("s") wifiSSID: String,
        @Field("p") password: String
    ): Response<String>

    @GET
    suspend fun turnDeviceOn(@Url url : String) : Response<String>

    @GET
    suspend fun turnDeviceOff(@Url url : String) : Response<String>

}
