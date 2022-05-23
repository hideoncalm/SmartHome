package com.quyen.smarthome.data.source.remote

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.quyen.smarthome.data.HouseDataSource
import com.quyen.smarthome.data.model.Home
import com.quyen.smarthome.utils.Constant
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HomeRemoteDataSource @Inject constructor(
    private var firebaseDatabase : FirebaseDatabase
) : HouseDataSource.Remote{

    private var homeReference : DatabaseReference = firebaseDatabase.getReference(Constant.HOME_PATH)
    private var houses : MutableList<Home> = mutableListOf()

    override suspend fun getHome(homeId: String): Home {
        val snapshot = homeReference.get().await()
        for (snap: DataSnapshot in snapshot.children) {
            val house: Home? = snap.getValue(Home::class.java)
            house?.let {
                houses.add(house)
            }
        }
        return houses[0]
    }

    override suspend fun updateHome(home: Home): Boolean {
        return homeReference.child(home.home_id).setValue(home).isSuccessful
    }

    override suspend fun insertHome(home: Home): Boolean {
        return homeReference.child(home.home_id).setValue(home).isSuccessful
    }

    override suspend fun deleteHome(home: Home): Boolean {
        return homeReference.child(home.home_id).removeValue().isSuccessful
    }

    override suspend fun getHomes(): List<Home> {
        val snapshot = homeReference.get().await()
        for (snap: DataSnapshot in snapshot.children) {
            val house: Home? = snap.getValue(Home::class.java)
            house?.let {
                houses.add(house)
            }
        }
        return houses
    }
}
