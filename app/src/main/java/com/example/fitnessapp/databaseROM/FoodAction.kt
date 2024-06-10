package com.example.fitnessapp.databaseROM

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "FoodAction",
    foreignKeys = [ForeignKey(entity = Food::class, parentColumns = ["id"], childColumns = ["foodID"])]
)
data class FoodAction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val foodID: Int,
    val action: Action,
    val date: Date,
    val amount: Float
)

enum class Action {
    EATEN, ADDED, COOKED, DISPOSED
}