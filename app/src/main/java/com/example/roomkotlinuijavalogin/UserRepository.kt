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

    suspend fun findUserByNameRepository(name:String):Array<User>{
        //Log.d("LoginRep",""+userDao.findByName(name).size )
         val result = userDao.findByName(name)
         Log.d("LoginV","UserRepository.findUserByNameRepository: size "+ (result.size))
        return result
    }
}