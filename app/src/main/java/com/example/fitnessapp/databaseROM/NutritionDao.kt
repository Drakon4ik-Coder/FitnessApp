package com.example.fitnessapp.databaseROM

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface NutritionDao {
    @Query("SELECT * FROM Nutrient")
    fun getAllNutrient(): List<Nutrient>
    @Query("SELECT * FROM Nutrient WHERE name = :name LIMIT 1")
    fun getNutrientByName(name: String): Nutrient
    @Insert
    fun insertNutrient(nutrient: Nutrient): Long
    @Delete
    suspend fun deleteNutrient(nutrient: Nutrient)
    @Insert
    fun insertFoodNutrition(foodNutrition: FoodNutrition): Long
    @Delete
    suspend fun deleteFoodNutrition(foodNutrition: FoodNutrition)
    @Transaction
    @Query("SELECT Nutrient.*, FoodNutrition.amount, FoodNutrition.portion FROM Food JOIN FoodNutrition ON Food.id = FoodNutrition.foodId JOIN Nutrient ON FoodNutrition.nutrientId = Nutrient.id WHERE foodId = :foodId")
    fun getFoodNutrition(foodId: Int): List<NutrientWithAmount>

    @Transaction
    fun getFoodNutrition(food: Food): List<NutrientWithAmount> {
        return getFoodNutrition(food.id)
    }



}