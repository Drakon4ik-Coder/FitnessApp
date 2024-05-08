package com.example.fitnessapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddMealActivity : AppCompatActivity() {

    private lateinit var addMealButton: Button
    private lateinit var caloriesInput: EditText
    private lateinit var ingredientInput: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_meal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        addMealButton = findViewById(R.id.add_meal)
        caloriesInput = findViewById(R.id.calories_input)
        ingredientInput = findViewById(R.id.ingredient_name_input)
        addMealButton.setOnClickListener {
            val inputCalories = caloriesInput.text.toString().toIntOrNull()
            val ingredientName = ingredientInput.text.toString()
            if (inputCalories != null && inputCalories > 0) {
                val calorie = Nutrient("calories", inputCalories.toDouble(), "kkal")
                val ingredient = Ingredient(ingredientName)
                ingredient.addNutrient(calorie)
                val meal = Meal("Tasty ass meal")
                meal.addIngredient(ingredient)
                val intent = Intent()
                intent.putExtra("meal", meal)
                setResult(RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show()
            }
        }
    }
}