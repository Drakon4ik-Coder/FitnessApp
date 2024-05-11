package com.example.fitnessapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TableLayout
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
    private val ingredientList : MutableList<Ingredient> = mutableListOf()
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
            for (ingredient in ingredientList) {
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
            val ingredient = intent?.getParcelableExtra("ingredient", Ingredient::class.java)
            ingredient?.let {
                ingredientList.add(ingredient)
            }
        }
    }
}