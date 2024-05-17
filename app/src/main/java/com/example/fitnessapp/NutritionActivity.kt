package com.example.fitnessapp

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class NutritionActivity : AppCompatActivity() {

    private lateinit var progressBarCalories: ProgressBar
    private lateinit var textViewProgressCalories: TextView
    private lateinit var buttonAddMeal: Button

    private var consumedCalories: Int = 0
    private val maxCalories: Int = 2000 // Set your maximum calories here

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrition)

        Thread {
            val db = Database.getInstance(this)
            var tmp = db.foodDao().getAll()
            Log.d("tmp", tmp.size.toString())
            db.foodDao().nukeTable()
            db.foodDao().insert(Food(1, "Carrot", ""))
            tmp = db.foodDao().getAll()
            Log.d("tmp", tmp.size.toString())
        }.start()

        // Initialize views
        progressBarCalories = findViewById(R.id.progressBarCalories)
        textViewProgressCalories = findViewById(R.id.textViewProgressCalories)
        buttonAddMeal = findViewById(R.id.buttonAddCalories)

        // Set click listener for the button to add calories
        buttonAddMeal.setOnClickListener {
            val intent = Intent(this, AddMealActivity::class.java)
            startForResult.launch(intent)
        }
    }

    val startForResult = registerForActivityResult<Intent, ActivityResult>(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val meal = intent?.getParcelableExtra("meal", Meal::class.java)
            meal?.let {
                updateProgress(meal)
            }
        }
    }

    private fun updateProgress(meal: Meal) {
        var caloriesAdd = 0
        val ingredients = meal.getIngredients()
        for (ingredient in ingredients) {
            caloriesAdd+= ingredient.getNutrient("calories")?.amount?.toInt() ?: 0
        }
        caloriesAdd.let { calories ->
            val animator = ValueAnimator.ofInt(consumedCalories, consumedCalories + calories)
            animator.duration = 1000 // Adjust the duration as needed
            animator.addUpdateListener { valueAnimator ->
                consumedCalories = valueAnimator.animatedValue as Int
                textViewProgressCalories.text =
                    getString(R.string.calories_text_progress, consumedCalories.toString(), maxCalories.toString())
                val progress = (consumedCalories.toDouble() / maxCalories.toDouble() * 10000).toInt()
                progressBarCalories.progress = progress
            }
            animator.start()
        }
    }
}
