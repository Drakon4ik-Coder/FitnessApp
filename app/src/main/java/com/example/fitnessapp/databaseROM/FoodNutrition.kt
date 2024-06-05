package com.example.fitnessapp.databaseROM

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["portion", "foodID", "nutrientID"],
    foreignKeys = [
        ForeignKey(entity = Food::class, parentColumns = ["id"], childColumns = ["foodID"]),
        ForeignKey(entity = Nutrient::class, parentColumns = ["id"], childColumns = ["nutrientID"])
    ]
)
data class FoodNutrition(
    val portion: Int,
    val foodID: Int,
    val nutrientID: Int,
    val amount: Float
)
