package com.quyen.smarthome.di

import android.content.Context
import android.content.SharedPreferences
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

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient = OkHttpClient()

    @Singleton
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient) : Retrofit =
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideSharedPreference(
        @ApplicationContext app : Context
    ) : SharedPreferences = app.getSharedPreferences("com.quyen.shared", Context.MODE_PRIVATE)
}
