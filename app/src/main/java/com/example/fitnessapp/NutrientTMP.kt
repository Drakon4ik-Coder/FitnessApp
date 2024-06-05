package com.example.fitnessapp

import java.io.Serializable

data class NutrientTMP(val name: String, var amount: Double, val unit: String) : Serializable