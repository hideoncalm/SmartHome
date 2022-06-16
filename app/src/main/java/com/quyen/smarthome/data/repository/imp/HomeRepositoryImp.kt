package com.quyen.smarthome.data.repository.imp

import androidx.lifecycle.LiveData
import com.quyen.smarthome.data.HouseDataSource
import com.quyen.smarthome.data.model.Home
import com.quyen.smarthome.data.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImp @Inject constructor(
    private val remote: HouseDataSource.Remote,
    private val local: HouseDataSource.Local
) : HomeRepository {

    override fun getLocalHomes(): LiveData<List<Home>> = local.getHomes()

    override suspend fun insertLocalHome(home: Home) = local.insertHome(home)

    override suspend fun updateLocalHome(home: Home) = local.updateHome(home)

    override suspend fun deleteLocalHome(home: Home) = local.deleteHome(home)

    override suspend fun getHome(homeId: String): Home = remote.getHome(homeId)

    override suspend fun updateHome(home: Home) = remote.updateHome(home)

    override suspend fun insertHome(home: Home): Boolean = remote.insertHome(home)

    override suspend fun deleteHome(home: Home) = remote.deleteHome(home)

    override suspend fun getHomes(): List<Home> = remote.getHomes()
}