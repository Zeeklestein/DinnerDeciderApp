package com.example.dinnerdeciderapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dinnerdeciderapp.model.Ingredient
import com.example.dinnerdeciderapp.model.Meal
import com.google.gson.GsonBuilder
import java.io.IOException

class ActivityNewMeal : AppCompatActivity() {

    private lateinit var addIngredientBtn: Button
    private lateinit var ingredientName: EditText
    private lateinit var ingredientQuantity: EditText
    private lateinit var mealName: EditText
    private lateinit var mealMethod: EditText

    private lateinit var mealImageView: ImageView
    private lateinit var mealImageString: String

    private lateinit var ingredientListRV: RecyclerView
    private lateinit var mIngredientListAdapterAddIngredient: AdapterAddIngredient

       private val mOnIngredientClickListener = object : OnItemClickListener {

        override fun onDelete(model: Ingredient) {
            mIngredientListAdapterAddIngredient.removeIngredient(model)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_edit_meal)

        // Initialise and set meal image string to blank to prevent crashing if no image is used
        mealImageString = ""

        //Initialise the recycler view
        ingredientListRV = findViewById(R.id.rv_ingredients)
        ingredientListRV.layoutManager = LinearLayoutManager(this)

        mIngredientListAdapterAddIngredient = AdapterAddIngredient(mOnIngredientClickListener)
        ingredientListRV.adapter = mIngredientListAdapterAddIngredient

        mealImageView = findViewById(R.id.imageView_editImage)

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

        val getResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            if(it.resultCode == Activity.RESULT_OK){

                mealImageString = it.data?.getStringExtra("MealUri").toString()

                // Set image view to image uri
                if(mealImageString.isNotBlank()) {
                    mealImageView.setImageURI(Uri.parse(mealImageString))
                }
            }
        }

        val imgBtnCamera = findViewById<ImageButton>(R.id.imgBtn_camera)
        imgBtnCamera.setOnClickListener {

            val intent = Intent (this, ActivityCamera::class.java).apply{}
            getResult.launch(intent)

        }
    }

    // Set menu for save button
    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.new_meal_menu, menu)
        return true
    }

    // Override to set what the menu items do
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.saveNewMeal -> {
                //Functionality for the save meal button TODO: Add to singleton meal array object?
                val name = mealName.text.toString()
                val method = mealMethod.text.toString()

                if (name.isNotBlank()) {

                    // Generate an ID for the new meal
                    val mealId = MealArrayObject.getNextItemId()

                    val newMeal = Meal(
                        mealId,
                        name,
                        mIngredientListAdapterAddIngredient.getIngredientList(),
                        method,
                        mealImageString
                    )

                    val gson = GsonBuilder().setPrettyPrinting().create()

                    //Add the new meal to the MealArrayObject array list
                    MealArrayObject.singletonMealArray.add(newMeal)

                    //Convert the array list to json
                    val finalMealListString = gson.toJson(MealArrayObject.singletonMealArray)

                    //Write the final json string to file
                    try {
                        JsonMealData().writeJSONMealData(this, finalMealListString.toByteArray())
                        //Show toast to notify that the meal was added successfully
                        val toast = Toast.makeText(
                            this,
                            "'${newMeal.mealName}' Added Successfully",
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                        //Intent to automatically return to the Manage Meals Activity
                        val intent = Intent(this, ActivityMainActivity::class.java).apply {}

                        /* Use flag to prevent the back button returning to the new meal activity and
                        causing index out of bounds exception */
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

                        // Return to the main activity
                        startActivity(intent)
                    } catch (ex: IOException) {
                        ex.printStackTrace()
                    }
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

}