package com.quyen.smarthome.data

import com.quyen.smarthome.data.model.User

interface UserDataSource {
    interface Remote{
        suspend fun insertUser(user: User)
        suspend fun deleteUser(userId: String)
        suspend fun updateUser(user: User)
        suspend fun getUsers() : List<User>?
        suspend fun getUserById(id : String) : User?
    }
}