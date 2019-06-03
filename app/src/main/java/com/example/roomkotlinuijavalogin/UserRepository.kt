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
         Log.d("Login","UserRepository.findUserByNameRepository: size "+ (result.size))
        return result
    }

    suspend fun findUserByNameAndPasswordRepository(name:String, password:String):Array<User>{
        val result= userDao.findByNameAndPassword(name, password)
        Log.d("Login","UserRepository.findUserByNameAndPasswordRepository: size "+ (result.size))
        return result
    }

    fun getUserByNameAndPassword(name:String, password:String):LiveData<List<User>>{
        return userDao.getUserByNameAndPassword(name, password)
    }
}