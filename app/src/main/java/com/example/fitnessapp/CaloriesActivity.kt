package com.example.fitnessapp

import android.animation.ValueAnimator
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CaloriesActivity : AppCompatActivity() {

    private lateinit var progressBarCalories: ProgressBar
    private lateinit var textViewProgressCalories: TextView
    private lateinit var editTextCalories: EditText
    private lateinit var buttonAddCalories: Button

    private var consumedCalories: Int = 0
    private val maxCalories: Int = 2000 // Set your maximum calories here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calories)

        // Initialize views
        progressBarCalories = findViewById(R.id.progressBarCalories)
        textViewProgressCalories = findViewById(R.id.textViewProgressCalories)
        editTextCalories = findViewById(R.id.editTextCalories)
        buttonAddCalories = findViewById(R.id.buttonAddCalories)

        // Set click listener for the button to add calories
        buttonAddCalories.setOnClickListener {
            addCalories()
        }
    }

    private fun addCalories() {
        val caloriesToAdd = editTextCalories.text.toString().toIntOrNull()
        caloriesToAdd?.let { calories ->
            val animator = ValueAnimator.ofInt(consumedCalories, consumedCalories + calories)
            animator.duration = 1000 // Adjust the duration as needed
            animator.addUpdateListener { valueAnimator ->
                consumedCalories = valueAnimator.animatedValue as Int
                updateProgress()
            }
            animator.start()
            editTextCalories.text.clear()
        }
    }

    private fun updateProgress() {
        textViewProgressCalories.text = "$consumedCalories/$maxCalories"
        val progress = (consumedCalories.toDouble() / maxCalories.toDouble() * 100).toInt()
        progressBarCalories.progress = progress
    }
}
