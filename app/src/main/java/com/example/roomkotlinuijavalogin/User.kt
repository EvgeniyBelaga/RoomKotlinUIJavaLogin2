package com.example.roomkotlinuijavalogin

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
class User(@PrimaryKey
//        @PrimaryKey(autoGenerate = true)
//        var id: Int,

           @ColumnInfo(name = "column_userName") val userName: String,
           @ColumnInfo(name = "column_password") val password: String ) {
}