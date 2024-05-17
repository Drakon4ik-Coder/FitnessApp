package com.example.fitnessapp

import android.content.Context
import androidx.room.Room

object Database {
    @Volatile
    private var instance: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        synchronized(this) {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "database"
                ).build()
            }
            return instance as AppDatabase
        }
    }
}