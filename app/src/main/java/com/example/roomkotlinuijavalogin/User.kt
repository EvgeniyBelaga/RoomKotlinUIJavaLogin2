package com.example.roomkotlinuijavalogin

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
class User(@PrimaryKey @ColumnInfo(name = "userName") val userName: String,
           @ColumnInfo(name = "password") val password: String ) {
}