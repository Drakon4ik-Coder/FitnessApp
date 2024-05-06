package com.example.fitnessapp

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
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
            addMeal()
        }
    }

    private fun addMeal() {
        startActivity(Intent(this, AddMealActivity::class.java).apply {
        })
    }

    private fun updateProgress() {
        textViewProgressCalories.text = "$consumedCalories/$maxCalories"
        val progress = (consumedCalories.toDouble() / maxCalories.toDouble() * 100).toInt()
        progressBarCalories.progress = progress
    }
}
