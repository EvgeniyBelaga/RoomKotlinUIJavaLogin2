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
    fun findByName(searchName: String): User

    @Insert
    suspend fun insert(user: User)

    @Query("DELETE FROM user_table")
    fun deleteAll()
}