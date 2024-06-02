package com.example.fitnessapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Food::class, MealIngredients::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
    abstract fun mealDao(): MealDao
}