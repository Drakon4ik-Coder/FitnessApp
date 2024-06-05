package com.example.fitnessapp.databaseROM

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
    fun getFoodByID(id: Int): Food

    @Query("SELECT * FROM Food WHERE name = :name")
    fun getFoodByName(name: String): List<Food>

    @Insert
    fun insertFood(food: Food): Long

    @Delete
    fun deleteFood(food: Food)

    @Insert
    fun insertFoodAction(foodAction: FoodAction): Long

    @Update
    fun updateFoodAction(foodAction: FoodAction)

    @Delete
    fun deleteFoodAction(foodAction: FoodAction)

    @Transaction
    @Query("SELECT * FROM FoodAction WHERE id = :id")
    fun getFoodActionById(id: Int): FoodAction

    @Transaction
    @Query("SELECT * FROM FoodAction WHERE foodID = :foodID ORDER BY date DESC")
    fun getFoodHistory(foodID: Int): List<FoodAction>

    @Transaction
    fun getFoodHistory(food: Food): List<FoodAction> {
        return getFoodHistory(food.id)
    }

    @Transaction
    @Query("SELECT SUM(IIF(`action` = 'ADDED', amount, -amount)) FROM FoodAction WHERE foodID = :foodID")
    fun getAvailableForFood(foodID: Int): Int

    @Transaction
    fun getAvailableForFood(food: Food): Int {
        return getAvailableForFood(food.id)
    }

    @Query("DELETE FROM food")
    fun nukeTable()

}