package com.quyen.smarthome.data

import com.quyen.smarthome.data.model.Home

interface HouseDataSource {
    interface Remote {
        suspend fun getHome(homeId: String) : Home
        suspend fun updateHome(home: Home) : Boolean
        suspend fun insertHome(home: Home) : Boolean
        suspend fun deleteHome(home: Home) : Boolean
        suspend fun getHomes() : List<Home>
    }
}
