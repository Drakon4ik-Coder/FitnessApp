package com.example.fitnessapp

import java.io.Serializable

data class Nutrient(val name: String, var amount: Double, val unit: String) : Serializable