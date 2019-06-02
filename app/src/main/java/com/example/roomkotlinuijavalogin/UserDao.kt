package com.example.roomkotlinuijavalogin

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * from user_table ORDER BY userName ASC")
    fun getAllUsers(): LiveData<List<User>>

    @Insert
    suspend fun insert(user: User)

    @Query("DELETE FROM user_table")
    fun deleteAll()
}