package com.quyen.smarthome.data.source.remote

import com.google.firebase.database.*
import com.quyen.smarthome.data.UserDataSource
import com.quyen.smarthome.data.model.User
import com.quyen.smarthome.utils.Constant.USER_PATH
import timber.log.Timber
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) : UserDataSource.Remote {

    private val users = mutableListOf<User>()

    private var userDatabaseRef: DatabaseReference = firebaseDatabase.getReference(USER_PATH)

    override suspend fun getUsers(): List<User>? {
        userDatabaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snap in snapshot.children) {
                    val user: User? = snap.getValue(User::class.java)
                    user?.let {
                        users.add(it)
                    }
                    Timber.d("userSize ${users.size}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        return users
    }

    override suspend fun getUserById(id: String): User? {
        return null
    }


    override suspend fun insertUser(user: User) {
        userDatabaseRef.child(user.id).setValue(user)
    }

    override suspend fun deleteUser(userId: String) {
        userDatabaseRef.child(userId).removeValue()
    }

    override suspend fun updateUser(user: User) {
        userDatabaseRef.child(user.id).setValue(user)
    }
}
