package com.example.roomkotlinuijavalogin

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {

    val allUsers: LiveData<List<User>> = userDao.getAllUsers()

    @WorkerThread
    suspend fun insert(user: User) {
        Log.d("Login", "UserReoisitory.insert")
        userDao.insert(user)
    }

    fun delateAll(){
        userDao.deleteAll()
    }

    suspend fun findUserByName(name:String):Array<User>{
        //Log.d("LoginRep",""+userDao.findByName(name).size )

         Log.d("LoginV","UserRepository. finUserByName: size "+ (userDao.findByName(name).size))
        return userDao.findByName(name)
    }
}