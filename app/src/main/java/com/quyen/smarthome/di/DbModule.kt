package com.quyen.smarthome.di

import com.google.firebase.database.FirebaseDatabase
import com.quyen.smarthome.data.DeviceDataSource
import com.quyen.smarthome.data.HouseDataSource
import com.quyen.smarthome.data.RoomDataSource
import com.quyen.smarthome.data.repository.*
import com.quyen.smarthome.data.repository.imp.*
import com.quyen.smarthome.data.source.local.DeviceLocalDataSource
import com.quyen.smarthome.data.source.local.HomeLocalDataSource
import com.quyen.smarthome.data.source.local.RoomLocalDataSource
import com.quyen.smarthome.data.source.local.TimeDao
import com.quyen.smarthome.data.source.remote.DeviceRemoteDataSource
import com.quyen.smarthome.data.source.remote.HomeRemoteDataSource
import com.quyen.smarthome.data.source.remote.RoomRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideDeviceRemote(firebase: FirebaseDatabase): DeviceDataSource.Remote =
        DeviceRemoteDataSource(firebase)

    @Provides
    @Singleton
    fun provideDeviceLocal(timeDao: TimeDao): DeviceDataSource.Local =
        DeviceLocalDataSource(timeDao)

    @Provides
    @Singleton
    fun provideDeviceRepository(
        local: DeviceDataSource.Local,
        remote: DeviceDataSource.Remote
    ): DeviceRepository = DeviceRepositoryImp(local, remote)

    @Provides
    @Singleton
    fun provideTimeRepository(timeDao: TimeDao): TimeRepository = TimeRepositoryImp(timeDao)

    @Singleton
    @Provides
    fun provideRoomDSRemote(firebase: FirebaseDatabase): RoomDataSource.Remote =
        RoomRemoteDataSource(firebase)

    @Singleton
    @Provides
    fun provideRoomDSLocal(timeDao: TimeDao): RoomDataSource.Local = RoomLocalDataSource(timeDao)

    @Provides
    @Singleton
    fun provideRoomRepo(
        local: RoomDataSource.Local,
        remote: RoomDataSource.Remote
    ): RoomRepository = RoomRepositoryImp(remote, local)

    @Singleton
    @Provides
    fun provideSceneRepo(timeDao: TimeDao): SceneRepository = SceneRepositoryImp(timeDao)

    @Singleton
    @Provides
    fun provideHomeRemote(firebase: FirebaseDatabase): HouseDataSource.Remote =
        HomeRemoteDataSource(firebase)

    @Singleton
    @Provides
    fun provideHomeLocal(timeDao: TimeDao): HouseDataSource.Local = HomeLocalDataSource(timeDao)

    @Provides
    @Singleton
    fun provideHomeRepository(
        local: HouseDataSource.Local,
        remote: HouseDataSource.Remote
    ): HomeRepository = HomeRepositoryImp(remote, local)

}
