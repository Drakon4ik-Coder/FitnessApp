package com.example.fitnessapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.round

class CaloriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calories)

        val max = 2200
        var progress = 0

        val calBar = findViewById<CircularProgressBar>(R.id.caloriesProgressBar)
        calBar.progressMax = max.toFloat()
        calBar.progress = progress.toFloat()

        val calText = findViewById<TextView>(R.id.caloriesProgressText)
        calText.text = String.format("%d/%d", progress, max)

        val button = findViewById<Button>(R.id.calloriesButton)
        button.setOnClickListener {
            val calAdd = findViewById<EditText>(R.id.caloriesInput)
            var addValue = calAdd.text.toString().toInt()
            var tmpProg = calBar.progress.toInt()
            progress+=addValue
            if (progress > max) {
                addValue = max - tmpProg
                Log.d("TAG", "Debug: $addValue")
            }
            var updateSpeed = round(2000F/addValue).toLong()
            if (updateSpeed < 1) {
                updateSpeed = 1
            }
            calText.text = String.format("%d/%d", progress, max)
            CoroutineScope(Dispatchers.Default).launch {
                repeat(addValue) {
                    calBar.progress = tmpProg.toFloat()
                    tmpProg += 1
                    delay(updateSpeed)
                }
            }
            if(progress > max) {
                calBar.progressBarColor = Color.parseColor("#B53F4D")
            }
        }
    }
}