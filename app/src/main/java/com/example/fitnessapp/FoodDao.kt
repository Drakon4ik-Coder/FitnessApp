package com.example.fitnessapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FoodDao {
    @Query("SELECT * FROM food")
    fun getAll(): List<Food>

    @Insert
    fun insert(food: Food)

    @Delete
    fun delete(food: Food)

    @Query("DELETE FROM food")
    fun nukeTable()

}