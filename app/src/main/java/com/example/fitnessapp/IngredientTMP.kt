package com.example.fitnessapp

import java.io.Serializable

class IngredientTMP(val name: String) : Serializable {
    private val nutrientsMap = mutableMapOf<String, Nutrient>()

    fun addNutrient(nutrient: Nutrient) {
        if (nutrientsMap.containsKey(nutrient.name)) {
            throw IllegalArgumentException("Nutrient with name ${nutrient.name} already exists in the Ingredient.")
        }
        nutrientsMap[nutrient.name] = nutrient
    }

    fun getNutrient(name: String): Nutrient? {
        return nutrientsMap[name]
    }

    fun getAllNutrients(): List<Nutrient> {
        return nutrientsMap.values.toList()
    }
}