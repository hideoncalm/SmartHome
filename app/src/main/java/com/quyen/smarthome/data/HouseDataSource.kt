package com.quyen.smarthome.data

import androidx.lifecycle.LiveData
import com.quyen.smarthome.data.model.Home

interface HouseDataSource {
    interface Remote {
        suspend fun getHome(homeId: String): Home
        suspend fun updateHome(home: Home): Boolean
        suspend fun insertHome(home: Home): Boolean
        suspend fun deleteHome(home: Home): Boolean
        suspend fun getHomes(): List<Home>
    }

    interface Local {
        fun getHomes(): LiveData<List<Home>>
        suspend fun insertHome(home: Home)
        suspend fun updateHome(home: Home)
        suspend fun deleteHome(home: Home)
    }
}
