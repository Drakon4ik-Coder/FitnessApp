package com.example.fitnessapp.databaseROM

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.fitnessapp.databaseROM.Food

@Entity(
    primaryKeys = ["mealID", "ingredientID"],
    foreignKeys = [
        ForeignKey(entity = Food::class, parentColumns = ["id"], childColumns = ["mealID"]),
        ForeignKey(entity = Food::class, parentColumns = ["id"], childColumns = ["ingredientID"])
    ]
)
data class MealIngredients(
    val mealID: Int,
    val ingredientID: Int,
    val amount: Float
)
