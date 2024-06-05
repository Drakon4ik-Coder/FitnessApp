package com.example.fitnessapp.databaseROM

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface MealDao {

    @Insert
    fun insert(mealIngredient: MealIngredients)

    @Delete
    fun delete(mealIngredient: MealIngredients)

    @Query("DELETE FROM MealIngredients")
    fun nukeTable()

    @Transaction
    @Query("""
        SELECT Food.*, MealIngredients.amount FROM Food 
        INNER JOIN MealIngredients ON Food.id = MealIngredients.ingredientID
        WHERE MealIngredients.mealID = :mealId
    """)
    fun getIngredientsForMeal(mealId: Int): List<FoodWithAmount>

    @Transaction
    @Query("""
        SELECT Food.*, MealIngredients.amount FROM Food 
        INNER JOIN MealIngredients ON Food.id = MealIngredients.mealID
        WHERE MealIngredients.ingredientID = :ingredientId
    """)
    fun getMealsContainingIngredient(ingredientId: Int): List<FoodWithAmount>

    @Transaction
    fun getIngredientsForMeal(meal: Food): List<FoodWithAmount> {
        return getIngredientsForMeal(meal.id)
    }

    @Transaction
    fun getMealsContainingIngredient(ingredient: Food): List<FoodWithAmount> {
        return getMealsContainingIngredient(ingredient.id)
    }
}