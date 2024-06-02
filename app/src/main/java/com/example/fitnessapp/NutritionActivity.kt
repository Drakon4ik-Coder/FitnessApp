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
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NutritionActivity : AppCompatActivity() {

    private lateinit var progressBarCalories: ProgressBar
    private lateinit var textViewProgressCalories: TextView
    private lateinit var buttonAddMeal: Button

    private var consumedCalories: Int = 0
    private val maxCalories: Int = 2000 // Set your maximum calories here

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrition)

        this.deleteDatabase("database")

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database"
        ).build()

        lifecycleScope.launch(Dispatchers.IO) {
            val foodDao = db.foodDao()
            val mealDao = db.mealDao()
            foodDao.nukeTable()
            mealDao.nukeTable()
            val cheese = foodDao.insert(Food(name = "Cheese")).toInt()
            val tomato = foodDao.insert(Food(name = "Tomato")).toInt()
            val pepperoni = foodDao.insert(Food(name = "Pepperoni")).toInt()
            val beef = foodDao.insert(Food(name = "Beef")).toInt()
            val pizza = foodDao.insert(Food(name = "Pizza")).toInt()
            val spaghetti = foodDao.insert(Food(name = "Spaghetti")).toInt()
            mealDao.insert(MealIngredients(pizza, cheese, 100f))
            mealDao.insert(MealIngredients(pizza, tomato, 100f))
            mealDao.insert(MealIngredients(pizza, pepperoni, 100f))
            mealDao.insert(MealIngredients(spaghetti, beef, 100f))
            mealDao.insert(MealIngredients(spaghetti, cheese, 100f))
            mealDao.insert(MealIngredients(spaghetti, tomato, 100f))
            Log.d("Food", mealDao.getIngredientsForMeal(pizza).toString())
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
