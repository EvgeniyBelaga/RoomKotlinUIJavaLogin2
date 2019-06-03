package com.example.roomkotlinuijavalogin

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table ORDER BY column_userName ASC")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE column_userName LIKE :searchName")
     suspend fun findByName(searchName: String):Array<User>

    @Query("SELECT * FROM user_table WHERE column_userName LIKE :searchName AND column_password LIKE :searchPassword")
    suspend fun findByNameAndPassword(searchName: String, searchPassword: String): Array<User>

    @Query("SELECT * FROM user_table WHERE column_userName LIKE :searchName AND column_password LIKE :searchPassword")
    fun getUserByNameAndPassword(searchName: String, searchPassword: String):LiveData<List<User>>

    @Insert
    suspend fun insert(user: User)

    @Query("DELETE FROM user_table")
    fun deleteAll()
}