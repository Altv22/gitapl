package com.example.gitapl.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Favourite::class],
    version = 1
)
abstract class UserDB: RoomDatabase() {
    companion object{
            var instance : UserDB? = null

            fun getDatabase(context: Context): UserDB?{
                if(instance==null){
                    synchronized(UserDB::class){
                        instance = Room.databaseBuilder(context.applicationContext,UserDB::class.java, "user_db").build()
                    }
                }
                return instance
            }
    }
    abstract fun favouriteDao(): FavouriteDao
}