package com.example.fitnessapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface MealDao {

    @Insert
    suspend fun insert(mealIngredient: MealIngredients)

    @Delete
    suspend fun delete(mealIngredient: MealIngredients)

    @Query("DELETE FROM MealIngredients")
    suspend fun nukeTable()

    @Transaction
    @Query("""
        SELECT Food.*, MealIngredients.amount FROM Food 
        INNER JOIN MealIngredients ON Food.id = MealIngredients.ingredientID
        WHERE MealIngredients.mealID = :mealId
    """)
    suspend fun getIngredientsForMeal(mealId: Int): List<FoodWithAmount>

    @Transaction
    @Query("""
        SELECT Food.*, MealIngredients.amount FROM Food 
        INNER JOIN MealIngredients ON Food.id = MealIngredients.mealID
        WHERE MealIngredients.ingredientID = :ingredientId
    """)
    suspend fun getMealsContainingIngredient(ingredientId: Int): List<FoodWithAmount>

    @Transaction
    suspend fun getIngredientsForMeal(meal: Food): List<FoodWithAmount> {
        return getIngredientsForMeal(meal.id)
    }

    @Transaction
    suspend fun getMealsContainingIngredient(ingredient: Food): List<FoodWithAmount> {
        return getMealsContainingIngredient(ingredient.id)
    }
}