package com.example.fitnessapp

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.fitnessapp.databaseROM.Action
import com.example.fitnessapp.databaseROM.Food
import com.example.fitnessapp.databaseROM.FoodAction
import com.example.fitnessapp.databaseROM.FoodDao
import com.example.fitnessapp.databaseROM.FoodNutrition
import com.example.fitnessapp.databaseROM.MealDao
import com.example.fitnessapp.databaseROM.MealIngredients
import com.example.fitnessapp.databaseROM.Nutrient
import com.example.fitnessapp.databaseROM.NutrientWithAmount
import com.example.fitnessapp.databaseROM.NutritionDao
import com.example.fitnessapp.databaseROM.Unit
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar
import java.util.Date

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    companion object {
        private lateinit var db: AppDatabase
        private lateinit var foodDao: FoodDao
        private lateinit var mealDao: MealDao
        private lateinit var nutritionDao: NutritionDao
        @BeforeClass
        @JvmStatic
        fun setUp() {
            // Create an in-memory database for testing
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
            foodDao = db.foodDao()
            mealDao = db.mealDao()
            nutritionDao = db.nutritionDao()
        }

        @AfterClass
        @JvmStatic
        fun tearDown() {
            // Close the database
            db.close()
        }
    }

    @Before
    fun fillTables(){
        // Insert data into the tables
        // Food
        val cheese = Food(name = "Cheese")
        val tomato = Food(name = "Tomato")
        val pepperoni = Food(name = "Pepperoni")
        val pizza = Food(name = "Pizza")
        val cheeseID = foodDao.insertFood(cheese).toInt()
        val tomatoID = foodDao.insertFood(tomato).toInt()
        val pepperoniID = foodDao.insertFood(pepperoni).toInt()
        val pizzaID = foodDao.insertFood(pizza).toInt()
        // Meal Ingredients
        val pizzaCheese = MealIngredients(pizzaID, cheeseID, 100f)
        val pizzaTomato = MealIngredients(pizzaID, tomatoID, 100f)
        val pizzaPepperoni = MealIngredients(pizzaID, pepperoniID, 100f)
        mealDao.insert(pizzaCheese)
        mealDao.insert(pizzaTomato)
        mealDao.insert(pizzaPepperoni)
        // Food history
        val todayDate = System.currentTimeMillis()
        val cheeseAction1 = FoodAction(foodID = cheeseID, action = Action.ADDED, date = Date(todayDate-1000), amount = 500f)
        val cheeseAction2 = FoodAction(foodID = cheeseID, action = Action.EATEN, date = Date(todayDate+3000), amount = 100f)
        val cheeseAction3 = FoodAction(foodID = cheeseID, action = Action.COOKED, date = Date(todayDate-2000), amount = 50f)
        val cheeseAction4 = FoodAction(foodID = cheeseID, action = Action.DISPOSED, date = Date(todayDate+1000), amount = 40f)
        foodDao.insertFoodAction(cheeseAction1)
        foodDao.insertFoodAction(cheeseAction2)
        foodDao.insertFoodAction(cheeseAction3)
        foodDao.insertFoodAction(cheeseAction4)
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -2)
        val oldDate = calendar.timeInMillis
        val tomatoAction1 = FoodAction(foodID = tomatoID, action = Action.ADDED, date = Date(todayDate-5000), amount = 500f)
        val tomatoAction2 = FoodAction(foodID = tomatoID, action = Action.EATEN, date = Date(oldDate+1000), amount = 120f)
        val tomatoAction3 = FoodAction(foodID = tomatoID, action = Action.EATEN, date = Date(oldDate+2000), amount = 50f)
        val tomatoAction4 = FoodAction(foodID = tomatoID, action = Action.EATEN, date = Date(oldDate), amount = 200f)
        foodDao.insertFoodAction(tomatoAction1)
        foodDao.insertFoodAction(tomatoAction2)
        foodDao.insertFoodAction(tomatoAction3)
        foodDao.insertFoodAction(tomatoAction4)
        // Nutrients
        val calories = Nutrient(name = "Calories", shortName = "CAL")
        val caloriesID = nutritionDao.insertNutrient(calories).toInt()
        val carbs = Nutrient(name = "Carbs", shortName = "CAR")
        val carbsID = nutritionDao.insertNutrient(carbs).toInt()
        val protein = Nutrient(name = "Protein", shortName = "PRO")
        val proteinID = nutritionDao.insertNutrient(protein).toInt()
        // Food Nutrition
        val foodNutrition1 = FoodNutrition(portion = 100, unit = Unit.g, foodID = cheeseID, nutrientID = caloriesID, amount = 402f)
        val foodNutrition2 = FoodNutrition(portion = 100, unit = Unit.g, foodID = cheeseID, nutrientID = carbsID, amount = 1.3f)
        val foodNutrition3 = FoodNutrition(portion = 100, unit = Unit.g, foodID = cheeseID, nutrientID = proteinID, amount = 25f)
        nutritionDao.insertFoodNutrition(foodNutrition1)
        nutritionDao.insertFoodNutrition(foodNutrition2)
        nutritionDao.insertFoodNutrition(foodNutrition3)

    }

    @After
    fun clearTables() {
        // Clear all data from the tables
        foodDao.nukeActionTable()
        mealDao.nukeMealTable()
        nutritionDao.nukeFoodNutritionTable()
        nutritionDao.nukeNutrientTable()
        foodDao.nukeFoodTable()
    }

    @Test
    fun insertAndRetrieveNutrient() = runBlocking {
        // Given a nutrient
        val nutrient = Nutrient(name = "Vitamin A", shortName = "VA")

        // When inserting the nutrient into the database
        val id = nutritionDao.insertNutrient(nutrient).toInt()

        // Then the nutrient can be retrieved from the database
        val retrievedNutrient = nutritionDao.getNutrientByName("Vitamin A")
        assertThat(retrievedNutrient.id, equalTo(id))
    }

    @Test
    fun insertAndRetrieveFood() = runBlocking {
        // Given a food
        val food = Food(name = "Egg")

        // When inserting the food into the database
        val id = foodDao.insertFood(food).toInt()

        // Then the food can be retrieved from the database
        val retrievedFood = foodDao.getFoodByID(id)
        assertThat(retrievedFood.id, equalTo(id))
        assertThat(retrievedFood.name, equalTo("Egg"))
    }

    @Test
    fun retrieveMealIngredients() = runBlocking {
        // Given a meal and ingredients are inserted into the database
        // Then meal can be retrieved from the database
        val pizza = foodDao.getFoodByName("Pizza")
        // Then the ingredients for the meal can be retrieved from the database
        val retrievedIngredients = mealDao.getIngredientsForMeal(pizza.id)
        assertThat(retrievedIngredients.size, equalTo(3))
    }

    @Test
    fun retrieveFoodHistoryAndLeftover() = runBlocking {
        // Given a food and food actions are inserted into the database
        // Then meal can be retrieved from the database
        val cheese = foodDao.getFoodByName("Cheese")

        // Then the history and leftover for the food can be retrieved from the database
        val foodHistory = foodDao.getFoodHistory(cheese.id)
        assertThat(foodHistory.size, equalTo(4))
        val foodLeftover = foodDao.getAvailableForFood(cheese.id)
        assertThat(foodLeftover, equalTo(310))
    }

    @Test
    fun retrieveFoodNutrition() = runBlocking {
        // Given a food and food nutrition are inserted into the database
        // Then food and nutrient can be retrieved from the database
        val cheese = foodDao.getFoodByName("Cheese")
        val calories = nutritionDao.getNutrientByName("Calories")

        // Then the nutrition for the food can be retrieved from the database
        val foodNutrition = nutritionDao.getFoodNutrition(cheese.id)
        assertThat(foodNutrition.size, equalTo(3))
        assertThat(foodNutrition.contains(NutrientWithAmount(calories, 402f, 100)), equalTo(true))
    }

    @Test
    fun getEatenFoodByDate() = runBlocking {
        // Given a food and food actions are inserted into the database
        // Then food history can be retrieved from the database
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -2)
        val oldDate = Date(calendar.timeInMillis)
        val history = foodDao.getEatenFoodByDate(oldDate)
        assertThat(history.size, equalTo(3))
    }


}
