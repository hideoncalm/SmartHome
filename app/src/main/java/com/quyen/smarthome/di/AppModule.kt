package com.quyen.smarthome.di

import android.content.Context
import android.content.SharedPreferences
import android.net.wifi.WifiManager
import com.google.firebase.database.FirebaseDatabase
import com.quyen.smarthome.data.source.remote.util.APIConfig
import com.quyen.smarthome.data.source.remote.util.APIService
import com.quyen.smarthome.di.AppModule_ProvideAPIServiceFactory.create
import com.quyen.smarthome.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import java.util.concurrent.TimeUnit
import okhttp3.ResponseBody
import okio.IOException
import java.net.SocketTimeoutException
import okhttp3.logging.HttpLoggingInterceptor





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


}
