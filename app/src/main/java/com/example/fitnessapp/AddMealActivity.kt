package com.example.fitnessapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddMealActivity : AppCompatActivity() {

    private lateinit var addMealButton: Button
    private lateinit var addIngredientButton: ImageButton
    private lateinit var tableLayout: TableLayout
    private lateinit var mealName: EditText
    private val ingredientTMPList : MutableList<IngredientTMP> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_meal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        tableLayout = findViewById(R.id.ingredient_table)
        mealName = findViewById(R.id.meal_name)
        addIngredientButton = findViewById(R.id.add_ingredient)
        addIngredientButton.setOnClickListener {
            val intent = Intent(this, NewIngredientActivity::class.java)
            startForResult.launch(intent)
        }
        addMealButton = findViewById(R.id.add_meal)
        addMealButton.setOnClickListener {
            val meal = Meal(mealName.text.toString())
            for (ingredient in ingredientTMPList) {
                meal.addIngredient(ingredient)
            }
            val intent = Intent()
            intent.putExtra("meal", meal)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
    val startForResult = registerForActivityResult<Intent, ActivityResult>(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val ingredientTMP = intent?.getParcelableExtra("ingredient", IngredientTMP::class.java)
            ingredientTMP?.let {
                ingredientTMPList.add(ingredientTMP)
            }
            updateIngredientTable()
        }
    }

    private fun updateIngredientTable() {
        clearTableLayout(tableLayout)
        for(ingredient in ingredientTMPList) {
            val row = TableRow(this)
            val params = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            row.layoutParams = params
            val nameEditText = TextView(this)
            nameEditText.text = ingredient.name
            nameEditText.gravity = Gravity.CENTER
            row.addView(nameEditText)

            for(nutrient in NutrientsList.nutrients.keys) {
                val nutrientEditText = TextView(this)
                nutrientEditText.text = ingredient.getNutrient(nutrient)?.amount.toString()
                nutrientEditText.gravity = Gravity.CENTER
                row.addView(nutrientEditText)
            }

            tableLayout.addView(row)
        }
    }
    fun clearTableLayout(tableLayout: TableLayout) {
        // Start iterating from index 1 to skip the first row
        for (i in tableLayout.childCount - 1 downTo 1) {
            val childView = tableLayout.getChildAt(i)
            if (childView is TableRow) {
                tableLayout.removeViewAt(i)
            }
        }
    }
}