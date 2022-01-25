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
import com.google.gson.reflect.TypeToken
import java.io.IOException

class NewMealActivity : AppCompatActivity() {

    private lateinit var addIngredientBtn: Button
    private lateinit var saveMealBtn: Button
    private lateinit var ingredientName: EditText
    private lateinit var ingredientQuantity: EditText
    private lateinit var mealName: EditText
    private lateinit var mealMethod: EditText

    private lateinit var ingredientListRV: RecyclerView
    private lateinit var mIngredientListAdapter: AddIngredientAdapter

    private val mOnIngredientClickListener = object : OnIngredientClickListener {

        override fun onDelete(model: Ingredient) {
            mIngredientListAdapter.removeIngredient(model)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_meal)

        //Initialise the recycler view
        ingredientListRV = findViewById(R.id.rv_ingredients)
        ingredientListRV.layoutManager = LinearLayoutManager(this)

        mIngredientListAdapter = AddIngredientAdapter(mOnIngredientClickListener)
        ingredientListRV.adapter = mIngredientListAdapter

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
                val id = mIngredientListAdapter.getNextItemId()
                //Create the ingredient model
                val model = Ingredient(id, name, quantity)

                //add model to the adapter
                mIngredientListAdapter.addIngredient(model)

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

                val newMeal = MealModelClass(name, mIngredientListAdapter.getIngredientList(), method)

                val gson = GsonBuilder().setPrettyPrinting().create()

                //val newMealString = gson.toJson(newMeal, MealModelClass::class.java)
                val allMealsString = JsonMealData().getJSONMealData(this, this.filesDir)
                var mealList: ArrayList<MealModelClass> = ArrayList()

                //Check if the json string is blank/null
                if(!allMealsString.isNullOrBlank()){
                    //If not null, get the data from it
                    mealList = gson.fromJson(allMealsString,
                        object :TypeToken<ArrayList<MealModelClass>>(){}.type)
                }
                //Add the new meal to the array list
                mealList.add(newMeal)
                //Convert the array list to json
                val finalMealListString = gson.toJson(mealList)


                //Write the final json string to file
                try{
                    JsonMealData().writeJSONMealData(this, finalMealListString.toByteArray())
                    //Show toast to notify that the meal was added successfully
                    val toast = Toast.makeText(this, "'${newMeal.mealName}' Added Successfully", Toast.LENGTH_SHORT)
                    toast.show()
                    //Intent to automatically return to the Manage Meals Activity
                    val intent = Intent (this, ManageMealsActivity::class.java).apply{}
                    startActivity(intent)
                }
                catch (ex: IOException){
                    ex.printStackTrace()
                }
            }
        }
    }
}