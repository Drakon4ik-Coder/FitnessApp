package com.example.fitnessapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddMealActivity : AppCompatActivity() {

    private lateinit var addMealButton: Button
    private lateinit var caloriesInput: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_meal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        addMealButton = findViewById(com.example.fitnessapp.R.id.add_meal)
        caloriesInput = findViewById(R.id.calories_input)
        addMealButton.setOnClickListener {
            val inputCalories = caloriesInput.text.toString().toIntOrNull()
            if (inputCalories != null && inputCalories > 0) {
                val intent = Intent()
                intent.putExtra("calories", inputCalories)
                setResult(RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show()
            }
        }
    }
}