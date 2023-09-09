package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TasksEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun TasksDAO(): TasksDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabse(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (INSTANCE != null) {
                return tempInstance as AppDatabase
            }
            synchronized(this){
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "tasks"
                ).build()
                INSTANCE = db
                return db
            }
        }
    }
}