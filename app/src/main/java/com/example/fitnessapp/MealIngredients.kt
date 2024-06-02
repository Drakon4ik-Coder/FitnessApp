package com.example.fitnessapp

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "MealIngredients",
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
