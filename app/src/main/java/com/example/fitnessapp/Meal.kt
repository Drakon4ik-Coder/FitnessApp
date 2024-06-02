package com.example.fitnessapp

import java.io.Serializable

class Meal(val name: String) : Serializable {
    private val ingredientTMPS = mutableListOf<IngredientTMP>()

    fun addIngredient(ingredientTMP: IngredientTMP) {
        ingredientTMPS.add(ingredientTMP)
    }

    fun getIngredients(): List<IngredientTMP> {
        return ingredientTMPS.toList()
    }
}