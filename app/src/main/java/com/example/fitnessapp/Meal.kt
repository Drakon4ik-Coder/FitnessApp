package com.example.fitnessapp

class Meal(val name: String) {
    private val ingredients = mutableListOf<Ingredient>()

    fun addIngredient(ingredient: Ingredient) {
        ingredients.add(ingredient)
    }

    fun getIngredients(): List<Ingredient> {
        return ingredients.toList()
    }
}