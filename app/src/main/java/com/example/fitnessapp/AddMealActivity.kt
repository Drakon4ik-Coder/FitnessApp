package com.example.fitnessapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textview.MaterialTextView

class AddMealActivity : AppCompatActivity() {

    private lateinit var addMealButton: Button
    private lateinit var tableLayout: TableLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_meal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        tableLayout = findViewById(R.id.nutrition_table)

        addMealButton = findViewById(R.id.add_meal)
        addMealButton.setOnClickListener {
            val ingredient = Ingredient("Example Ingredient")

            for (i in 1 until tableLayout.childCount) {
                val row = tableLayout.getChildAt(i) as? TableRow
                row?.let {
                    val nutrientName = (row.getChildAt(0) as MaterialTextView).text.toString().lowercase()
                    val nutrientAmount = (row.getChildAt(1) as EditText).text.toString().toDoubleOrNull() ?: 0.0
                    val nutrient = Nutrient(nutrientName, nutrientAmount, "g")
                    ingredient.addNutrient(nutrient)
                }
            }
            val meal = Meal("Example Meal")
            meal.addIngredient(ingredient)

            val intent = Intent()
            intent.putExtra("meal", meal)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}