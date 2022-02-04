package com.example.dinnerdeciderapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dinnerdeciderapp.model.Ingredient
import com.example.dinnerdeciderapp.model.MealModelClass
import com.google.gson.GsonBuilder
import java.io.IOException

class NewMealActivity : AppCompatActivity() {

    private lateinit var addIngredientBtn: Button
    private lateinit var saveMealBtn: Button
    private lateinit var ingredientName: EditText
    private lateinit var ingredientQuantity: EditText
    private lateinit var mealName: EditText
    private lateinit var mealMethod: EditText

    private lateinit var ingredientListRV: RecyclerView
    private lateinit var mIngredientListAdapterAddIngredient: AdapterAddIngredient

    private val mOnIngredientClickListener = object : OnIngredientClickListener {

        override fun onDelete(model: Ingredient) {
            mIngredientListAdapterAddIngredient.removeIngredient(model)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_meal)

        //Initialise the recycler view
        ingredientListRV = findViewById(R.id.rv_ingredients)
        ingredientListRV.layoutManager = LinearLayoutManager(this)

        mIngredientListAdapterAddIngredient = AdapterAddIngredient(mOnIngredientClickListener)
        ingredientListRV.adapter = mIngredientListAdapterAddIngredient

        ingredientName = findViewById(R.id.editText_IngrName)
        ingredientQuantity = findViewById(R.id.editText_IngrQuantity)
        mealName = findViewById(R.id.editText_MealName)
        mealMethod = findViewById(R.id.editTextMulti_Method)

        //Functionality for the add ingredient button
        addIngredientBtn = findViewById(R.id.btn_addIngr)
        addIngredientBtn.setOnClickListener {

            val name = ingredientName.text.toString()
            val quantity = ingredientQuantity.text.toString()

            //Check if any input is blank
            if (name.isNotBlank() && quantity.isNotBlank()) {

                //Get id automatically using method in adapter
                val id = mIngredientListAdapterAddIngredient.getNextItemId()
                //Create the ingredient model
                val model = Ingredient(id, name, quantity)

                //add model to the adapter
                mIngredientListAdapterAddIngredient.addIngredient(model)

                //Reset input
                ingredientName.setText("")
                ingredientQuantity.setText("")
            }
        }

        //Functionality for the save meal button
        saveMealBtn = findViewById(R.id.btn_SaveMeal)
        saveMealBtn.setOnClickListener {

            val name = mealName.text.toString()
            val method = mealMethod.text.toString()

            if (name.isNotBlank()) {

                val newMeal = MealModelClass(name, mIngredientListAdapterAddIngredient.getIngredientList(), method)

                val gson = GsonBuilder().setPrettyPrinting().create()

                //Add the new meal to the MealArrayObject array list
                MealArrayObject.singletonMealArray.add(newMeal)
                //Convert the array list to json
                val finalMealListString = gson.toJson(MealArrayObject.singletonMealArray)

                //Write the final json string to file
                try{
                    JsonMealData().writeJSONMealData(this, finalMealListString.toByteArray())
                    //Show toast to notify that the meal was added successfully
                    val toast = Toast.makeText(this, "'${newMeal.mealName}' Added Successfully", Toast.LENGTH_SHORT)
                    toast.show()
                    //Intent to automatically return to the Manage Meals Activity
                    val intent = Intent (this, MainActivity::class.java).apply{}
                    startActivity(intent)
                }
                catch (ex: IOException){
                    ex.printStackTrace()
                }
            }
        }
    }
}