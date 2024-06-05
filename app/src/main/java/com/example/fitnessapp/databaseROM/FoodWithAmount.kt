package com.example.fitnessapp.databaseROM

import androidx.room.Embedded
import com.example.fitnessapp.databaseROM.Food

data class FoodWithAmount(
    @Embedded val food: Food,
    val amount: Float
)
