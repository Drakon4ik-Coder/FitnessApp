package com.example.fitnessapp

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class CaloriesActivity : AppCompatActivity() {

    private lateinit var progressBarCalories: ProgressBar
    private lateinit var textViewProgressCalories: TextView
    private lateinit var buttonAddMeal: Button

    private var consumedCalories: Int = 0
    private val maxCalories: Int = 2000 // Set your maximum calories here

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calories)

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
            updateProgress(intent?.getIntExtra("calories", 0) ?: 0)
        }
    }

    private fun addMeal() {
        startActivity(Intent(this, AddMealActivity::class.java).apply {
        })
    }

    private fun updateProgress(caloriesAdd: Int) {
        caloriesAdd.let { calories ->
            val animator = ValueAnimator.ofInt(consumedCalories, consumedCalories + calories)
            animator.duration = 1000 // Adjust the duration as needed
            animator.addUpdateListener { valueAnimator ->
                consumedCalories = valueAnimator.animatedValue as Int
                textViewProgressCalories.text = "$consumedCalories/$maxCalories"
                val progress = (consumedCalories.toDouble() / maxCalories.toDouble() * 100).toInt()
                progressBarCalories.progress = progress
            }
            animator.start()
        }
    }
}
