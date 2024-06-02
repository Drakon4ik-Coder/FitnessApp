package com.example.fitnessapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FoodDao {
    @Query("SELECT * FROM food")
    fun getAll(): List<Food>

    @Query("SELECT * FROM Food WHERE id = :id")
    suspend fun getFoodById(id: Int): Food

    @Query("SELECT * FROM Food WHERE name = :name")
    suspend fun getFoodByName(name: String): List<Food>

    @Insert
    suspend fun insert(food: Food): Long

    @Delete
    suspend fun delete(food: Food)

    @Query("DELETE FROM food")
    suspend fun nukeTable()

}