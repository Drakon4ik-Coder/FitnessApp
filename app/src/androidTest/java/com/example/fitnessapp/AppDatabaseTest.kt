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
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var db: AppDatabase
    private lateinit var foodDao: FoodDao
    private lateinit var mealDao: MealDao
    private lateinit var nutritionDao: NutritionDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        foodDao = db.foodDao()
        mealDao = db.mealDao()
        nutritionDao = db.nutritionDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAndRetrieveOneNutrient() = runBlocking {
        // Given a nutrient
        val nutrient = Nutrient(name = "Vitamin A", shortName = "VA")

        // When inserting the nutrient into the database
        val id = nutritionDao.insertNutrient(nutrient).toInt()

        // Then the nutrient can be retrieved from the database
        val retrievedNutrient = nutritionDao.getNutrientByName("Vitamin A")
        assertThat(retrievedNutrient.id, equalTo(id))
    }

    @Test
    fun insertAndRetrieveOneFood() = runBlocking {
        // Given a food
        val food = Food(name = "Cheese")

        // When inserting the food into the database
        val id = foodDao.insertFood(food).toInt()

        // Then the food can be retrieved from the database
        val retrievedFood = foodDao.getFoodByID(id)
        assertThat(retrievedFood.id, equalTo(id))
        assertThat(retrievedFood.name, equalTo("Cheese"))
    }

    @Test
    fun insertMealAndRetrieveIngredients() = runBlocking {
        // Given a meal and ingredients are inserted into the database
        val cheese = Food(name = "Cheese")
        val tomato = Food(name = "Tomato")
        val pepperoni = Food(name = "Pepperoni")
        val pizza = Food(name = "Pizza")
        val cheeseID = foodDao.insertFood(cheese).toInt()
        val tomatoID = foodDao.insertFood(tomato).toInt()
        val pepperoniID = foodDao.insertFood(pepperoni).toInt()
        val pizzaID = foodDao.insertFood(pizza).toInt()
        val pizzaCheese = MealIngredients(pizzaID, cheeseID, 100f)
        val pizzaTomato = MealIngredients(pizzaID, tomatoID, 100f)
        val pizzaPepperoni = MealIngredients(pizzaID, pepperoniID, 100f)
        mealDao.insert(pizzaCheese)
        mealDao.insert(pizzaTomato)
        mealDao.insert(pizzaPepperoni)

        // Then the ingredients for the meal can be retrieved from the database
        val retrievedIngredients = mealDao.getIngredientsForMeal(pizzaID)
        assertThat(retrievedIngredients.size, equalTo(3))
    }

    @Test
    fun insertFoodAndRetrieveHistoryAndLeftover() = runBlocking {
        // Given a food and food actions are inserted into the database
        val cheese = Food(name = "Cheese")
        val cheeseID = foodDao.insertFood(cheese).toInt()
        val cheeseAction1 = FoodAction(foodID = cheeseID, action = Action.ADDED, date = System.currentTimeMillis(), amount = 500f)
        val cheeseAction2 = FoodAction(foodID = cheeseID, action = Action.EATEN, date = System.currentTimeMillis(), amount = 100f)
        val cheeseAction3 = FoodAction(foodID = cheeseID, action = Action.COOKED, date = System.currentTimeMillis(), amount = 50f)
        val cheeseAction4 = FoodAction(foodID = cheeseID, action = Action.DISPOSED, date = System.currentTimeMillis(), amount = 40f)
        foodDao.insertFoodAction(cheeseAction1)
        foodDao.insertFoodAction(cheeseAction2)
        foodDao.insertFoodAction(cheeseAction3)
        foodDao.insertFoodAction(cheeseAction4)

        // Then the history and leftover for the food can be retrieved from the database
        val foodHistory = foodDao.getFoodHistory(cheeseID)
        assertThat(foodHistory.size, equalTo(4))
        val foodLeftover = foodDao.getAvailableForFood(cheeseID)
        assertThat(foodLeftover, equalTo(310))
    }

    @Test
    fun insertFoodAndRetrieveNutrition() = runBlocking {
        // Given a food and food nutrition are inserted into the database
        val cheese = Food(name = "Cheese")
        val cheeseID = foodDao.insertFood(cheese).toInt()
        val calories = Nutrient(name = "Calories", shortName = "CAL")
        val caloriesID = nutritionDao.insertNutrient(calories).toInt()
        val carbs = Nutrient(name = "Carbs", shortName = "CAR")
        val carbsID = nutritionDao.insertNutrient(carbs).toInt()
        val protein = Nutrient(name = "Protein", shortName = "PRO")
        val proteinID = nutritionDao.insertNutrient(protein).toInt()
        val foodNutrition1 = FoodNutrition(portion = 100, foodID = cheeseID, nutrientID = caloriesID, amount = 402f)
        val foodNutrition2 = FoodNutrition(portion = 100, foodID = cheeseID, nutrientID = carbsID, amount = 1.3f)
        val foodNutrition3 = FoodNutrition(portion = 100, foodID = cheeseID, nutrientID = proteinID, amount = 25f)
        nutritionDao.insertFoodNutrition(foodNutrition1)
        nutritionDao.insertFoodNutrition(foodNutrition2)
        nutritionDao.insertFoodNutrition(foodNutrition3)

        // Then the nutrition for the food can be retrieved from the database
        val foodNutrition = nutritionDao.getFoodNutrition(cheeseID)
        assertThat(foodNutrition.size, equalTo(3))
        assertThat(foodNutrition.contains(NutrientWithAmount(Nutrient(caloriesID,"Calories", "CAL"), 402f, 100)), equalTo(true))
    }


}
