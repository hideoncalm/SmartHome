package com.quyen.smarthome.data.repository

import androidx.lifecycle.LiveData
import com.quyen.smarthome.data.model.Home

interface HomeRepository {
    fun getLocalHomes(): LiveData<List<Home>>
    suspend fun insertLocalHome(home: Home)
    suspend fun updateLocalHome(home: Home)
    suspend fun deleteLocalHome(home: Home)

    suspend fun getHome(homeId: String): Home
    suspend fun updateHome(home: Home): Boolean
    suspend fun insertHome(home: Home): Boolean
    suspend fun deleteHome(home: Home): Boolean
    suspend fun getHomes(): List<Home>
}