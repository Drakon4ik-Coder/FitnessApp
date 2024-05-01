package com.example.fitnessapp

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
    private val maxCalories: Int = 1000 // Set your maximum calories here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calories)

        // Initialize views
        progressBarCalories = findViewById(R.id.progressBarCalories)
        textViewProgressCalories = findViewById(R.id.textViewProgressCalories)
        editTextCalories = findViewById(R.id.editTextCalories)
        buttonAddCalories = findViewById(R.id.buttonAddCalories)

        // Set initial progress text
        updateProgressText()

        // Set click listener for the button to add calories
        buttonAddCalories.setOnClickListener {
            addCalories()
        }
    }

    private fun addCalories() {
        val inputCalories = editTextCalories.text.toString().toIntOrNull()
        if (inputCalories != null && inputCalories > 0) {
            consumedCalories += inputCalories
            if (consumedCalories > maxCalories) {
                consumedCalories = maxCalories
            }
            updateProgress()
            updateProgressText()
            editTextCalories.text.clear()
        } else {
            // Handle invalid input
            // For example, show a toast message
            // Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateProgress() {
        val progress = (consumedCalories.toFloat() / maxCalories.toFloat() * 100).toInt()
        progressBarCalories.progress = progress
    }

    private fun updateProgressText() {
        textViewProgressCalories.text = "$consumedCalories/$maxCalories"
    }
}
