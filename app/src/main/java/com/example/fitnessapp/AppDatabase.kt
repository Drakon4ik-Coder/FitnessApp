package com.example.fitnessapp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fitnessapp.databaseROM.Food
import com.example.fitnessapp.databaseROM.FoodAction
import com.example.fitnessapp.databaseROM.FoodDao
import com.example.fitnessapp.databaseROM.FoodNutrition
import com.example.fitnessapp.databaseROM.MealDao
import com.example.fitnessapp.databaseROM.MealIngredients
import com.example.fitnessapp.databaseROM.Nutrient
import com.example.fitnessapp.databaseROM.NutritionDao

@Database(entities = [Food::class, MealIngredients::class, FoodAction::class, Nutrient::class, FoodNutrition::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
    abstract fun mealDao(): MealDao
    abstract fun nutritionDao(): NutritionDao
}