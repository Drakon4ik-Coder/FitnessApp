package com.example.fitnessapp

import java.io.Serializable

class Meal(val name: String) : Serializable {
    private val ingredients = mutableListOf<Ingredient>()

    fun addIngredient(ingredient: Ingredient) {
        ingredients.add(ingredient)
    }

    fun getIngredients(): List<Ingredient> {
        return ingredients.toList()
    }
}