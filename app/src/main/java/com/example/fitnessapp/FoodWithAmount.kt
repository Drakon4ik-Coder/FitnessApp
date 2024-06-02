package com.example.fitnessapp

import androidx.room.Embedded

data class FoodWithAmount(
    @Embedded val food: Food,
    val amount: Float
)
