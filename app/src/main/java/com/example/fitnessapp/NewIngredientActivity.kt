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

class NewIngredientActivity : AppCompatActivity() {
    private lateinit var addIngredientButton: Button
    private lateinit var tableLayout: TableLayout
    private lateinit var ingredientName: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_ingredient)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        tableLayout = findViewById(R.id.nutrition_table)
        ingredientName = findViewById(R.id.ingredientName)

        addIngredientButton = findViewById(R.id.add_ingredient_button)
        addIngredientButton.setOnClickListener {
            val ingredient = Ingredient(ingredientName.text.toString())

            for (i in 1 until tableLayout.childCount) {
                val row = tableLayout.getChildAt(i) as? TableRow
                row?.let {
                    val nutrientName = (row.getChildAt(0) as MaterialTextView).text.toString().lowercase()
                    val nutrientAmount = (row.getChildAt(1) as EditText).text.toString().toDoubleOrNull() ?: 0.0
                    val nutrient = Nutrient(nutrientName, nutrientAmount, "g")
                    ingredient.addNutrient(nutrient)
                }
            }

            val intent = Intent()
            intent.putExtra("ingredient", ingredient)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}