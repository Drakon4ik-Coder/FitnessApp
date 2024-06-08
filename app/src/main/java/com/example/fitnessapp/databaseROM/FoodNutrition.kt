package com.example.fitnessapp.databaseROM

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["portion", "unit", "foodID", "nutrientID"],
    foreignKeys = [
        ForeignKey(entity = Food::class, parentColumns = ["id"], childColumns = ["foodID"]),
        ForeignKey(entity = Nutrient::class, parentColumns = ["id"], childColumns = ["nutrientID"])
    ]
)
data class FoodNutrition(
    val portion: Int,
    val unit: Unit,
    val foodID: Int,
    val nutrientID: Int,
    val amount: Float
)

enum class Unit {
    g, ml, unit
}
