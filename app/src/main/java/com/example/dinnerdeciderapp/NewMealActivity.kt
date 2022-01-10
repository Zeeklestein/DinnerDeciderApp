package com.example.dinnerdeciderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dinnerdeciderapp.model.Ingredient
import com.example.dinnerdeciderapp.model.MealModelClass

class NewMealActivity : AppCompatActivity() {

    private lateinit var addIngredientBtn: Button
    private lateinit var saveMealBtn: Button
    private lateinit var ingredientName: EditText
    private lateinit var ingredientQuantity: EditText
    private lateinit var mealName: EditText

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
        //ingredientListRV.setHasFixedSize(true)

        mIngredientListAdapter = AddIngredientAdapter(this, mOnIngredientClickListener)
        ingredientListRV.adapter = mIngredientListAdapter

        ingredientName = findViewById(R.id.editText_IngrName)
        ingredientQuantity = findViewById(R.id.editText_IngrQuantity)
        mealName = findViewById(R.id.editText_MealName)

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
            } //TODO("Add Snack bar or toast for blank input")

        }

        //Functionality for the save meal button
        saveMealBtn = findViewById(R.id.btn_SaveMeal)
        saveMealBtn.setOnClickListener {

            val name = mealName.text.toString()

            if (name.isNotBlank()){
                val newMeal = MealModelClass(name, mIngredientListAdapter.getIngredientList())

                //TODO Meal is saved to the json file
            }


        }

    }

}