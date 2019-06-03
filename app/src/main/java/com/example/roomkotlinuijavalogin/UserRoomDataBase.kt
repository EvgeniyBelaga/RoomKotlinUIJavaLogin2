package com.example.roomkotlinuijavalogin

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = arrayOf(User::class), version = 1)
public abstract class UserRoomDataBase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserRoomDataBase? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope
        ): UserRoomDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserRoomDataBase::class.java,
                        "User_database"
                ).addCallback(UserDatabaseCallback(scope)).build()
                INSTANCE = instance
                Log.d("Login", "UserRoomDataBase.getDatabase")
                return instance
            }
        }
    }

    private class UserDatabaseCallback(
            private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.userDao())
                }
            }
            Log.d("Login", "UserRoomDataBase.UserDatabaseCallback")
        }

        suspend fun populateDatabase(userDao: UserDao) {

            Log.d("Login1", "UserRoomDataBase.populateDatabase")
            userDao.deleteAll()

            var user = User("user1", "111")
            userDao.insert(user)
            user = User("user2", "222")
            userDao.insert(user)
            user = User("user3", "333")
            userDao.insert(user)

            Log.d("Login", "UserRoomDataBase.populateDatabase")
        }
    }

}
