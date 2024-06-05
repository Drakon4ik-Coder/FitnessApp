package com.example.fitnessapp.databaseROM

import androidx.annotation.Size
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["shortName"], unique = true), Index(value = ["name"], unique = true)])
data class Nutrient(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    @Size(min = 1, max = 3)
    val shortName: String
)
