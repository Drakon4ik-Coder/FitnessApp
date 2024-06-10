package com.example.fitnessapp.databaseROM

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["name"], unique = true)])
data class Food(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val image: String = ""
)
