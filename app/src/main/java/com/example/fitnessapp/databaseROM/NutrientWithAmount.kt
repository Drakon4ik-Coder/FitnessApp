package com.example.fitnessapp.databaseROM

import androidx.room.Embedded

data class NutrientWithAmount(
    @Embedded val nutrition: Nutrient,
    val amount: Float,
    val portion: Int
)
