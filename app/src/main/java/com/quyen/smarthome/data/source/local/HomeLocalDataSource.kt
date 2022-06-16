package com.quyen.smarthome.data.source.local

import androidx.lifecycle.LiveData
import com.quyen.smarthome.data.HouseDataSource
import com.quyen.smarthome.data.model.Home
import javax.inject.Inject

class HomeLocalDataSource @Inject constructor(
    private val timeDao: TimeDao
) : HouseDataSource.Local{
    override fun getHomes(): LiveData<List<Home>> = timeDao.getHomes()

    override suspend fun insertHome(home: Home) = timeDao.insertHome(home)

    override suspend fun updateHome(home: Home) = timeDao.updateHome(home)

    override suspend fun deleteHome(home: Home) = timeDao.deleteHome(home)
}