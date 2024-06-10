package com.example.fitnessapp.databaseROM

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import java.util.Date

@Dao
interface FoodDao {
    @Query("SELECT * FROM food")
    fun getAllFood(): List<Food>

    @Query("SELECT * FROM Food WHERE id = :id")
    fun getFoodByID(id: Int): Food

    @Query("SELECT * FROM Food WHERE name = :name")
    fun getFoodByName(name: String): Food

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

    @Query("SELECT Food.*, FoodAction.amount FROM Food JOIN FoodAction ON Food.id = FoodAction.foodID WHERE date(ROUND(date / 1000), 'unixepoch') = date(ROUND(:searchDate / 1000), 'unixepoch') AND `action` = 'EATEN' ORDER BY date ASC")
    fun getEatenFoodByDate(searchDate: Date): List<FoodWithAmount>

    @Query("DELETE FROM food")
    fun nukeFoodTable()

    @Query("DELETE FROM foodAction")
    fun nukeActionTable()
}