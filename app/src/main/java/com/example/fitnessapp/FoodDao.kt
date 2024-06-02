package com.example.fitnessapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface FoodDao {
    @Query("SELECT * FROM food")
    fun getAllFood(): List<Food>

    @Query("SELECT * FROM Food WHERE id = :id")
    suspend fun getFoodById(id: Int): Food

    @Query("SELECT * FROM Food WHERE name = :name")
    suspend fun getFoodByName(name: String): List<Food>

    @Insert
    suspend fun insertFood(food: Food): Long

    @Delete
    suspend fun deleteFood(food: Food)

    @Insert
    suspend fun insertFoodAction(foodAction: FoodAction): Long

    @Update
    suspend fun updateFoodAction(foodAction: FoodAction)

    @Delete
    suspend fun deleteFoodAction(foodAction: FoodAction)

    @Transaction
    @Query("SELECT * FROM FoodAction WHERE id = :id")
    suspend fun getFoodActionById(id: Int): FoodAction

    @Transaction
    @Query("SELECT * FROM FoodAction WHERE foodID = :foodID")
    suspend fun getHistoryForFood(foodID: Int): List<FoodAction>

    @Transaction
    suspend fun getHistoryForFood(food: Food): List<FoodAction> {
        return getHistoryForFood(food.id)
    }

    @Transaction
    @Query("SELECT SUM(IIF(`action` = 'ADDED', amount, -amount)) FROM FoodAction WHERE foodID = :foodID")
    fun getAvailableForFood(foodID: Int): Int

    @Transaction
    fun getAvailableForFood(food: Food): Int {
        return getAvailableForFood(food.id)
    }

    @Query("DELETE FROM food")
    suspend fun nukeTable()

}