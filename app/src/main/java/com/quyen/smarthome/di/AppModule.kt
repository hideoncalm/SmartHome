package com.quyen.smarthome.di

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.database.FirebaseDatabase
import com.quyen.smarthome.data.source.remote.util.APIConfig
import com.quyen.smarthome.data.source.remote.util.APIService
import com.quyen.smarthome.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.MqttClient


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client =  OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(logging)
//        client.interceptors().add(object : Interceptor{
//            override fun intercept(chain: Interceptor.Chain): Response {
//            }
//        })

        return client.build()
    }


    @Singleton
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(APIConfig.ESP_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideAPIService(retrofit: Retrofit): APIService = retrofit.create(APIService::class.java)

    @Singleton
    @Provides
    fun provideSharedPreference(
        @ApplicationContext app: Context
    ): SharedPreferences = app.getSharedPreferences("com.quyen.shared", Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase =
        FirebaseDatabase.getInstance("https://smarthome-e6d0f-default-rtdb.asia-southeast1.firebasedatabase.app")


    @Singleton
    @Provides
    fun provideMQTTClient(@ApplicationContext app: Context): MqttAndroidClient {
        val clientId = MqttClient.generateClientId()
        return MqttAndroidClient(app, Constant.MQTT_SERVER_URL, clientId)
    }
}
