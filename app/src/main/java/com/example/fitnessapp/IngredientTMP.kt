package com.example.fitnessapp

import java.io.Serializable

class IngredientTMP(val name: String) : Serializable {
    private val nutrientsMap = mutableMapOf<String, NutrientTMP>()

    fun addNutrient(nutrientTMP: NutrientTMP) {
        if (nutrientsMap.containsKey(nutrientTMP.name)) {
            throw IllegalArgumentException("Nutrient with name ${nutrientTMP.name} already exists in the Ingredient.")
        }
        nutrientsMap[nutrientTMP.name] = nutrientTMP
    }

    fun getNutrient(name: String): NutrientTMP? {
        return nutrientsMap[name]
    }

    fun getAllNutrients(): List<NutrientTMP> {
        return nutrientsMap.values.toList()
    }
}