package com.quyen.smarthome.di

import com.google.firebase.database.FirebaseDatabase
import com.quyen.smarthome.data.DeviceDataSource
import com.quyen.smarthome.data.RoomDataSource
import com.quyen.smarthome.data.repository.DeviceRepository
import com.quyen.smarthome.data.repository.RoomRepository
import com.quyen.smarthome.data.repository.SceneRepository
import com.quyen.smarthome.data.repository.TimeRepository
import com.quyen.smarthome.data.repository.imp.DeviceRepositoryImp
import com.quyen.smarthome.data.repository.imp.RoomRepositoryImp
import com.quyen.smarthome.data.repository.imp.SceneRepositoryImp
import com.quyen.smarthome.data.repository.imp.TimeRepositoryImp
import com.quyen.smarthome.data.source.local.DeviceLocalDataSource
import com.quyen.smarthome.data.source.local.RoomLocalDataSource
import com.quyen.smarthome.data.source.local.TimeDao
import com.quyen.smarthome.data.source.remote.DeviceRemoteDataSource
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
}
