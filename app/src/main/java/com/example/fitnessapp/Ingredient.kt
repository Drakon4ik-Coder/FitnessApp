package com.example.fitnessapp

class Ingredient(val name: String) {
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