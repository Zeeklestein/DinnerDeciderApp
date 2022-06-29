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
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dinnerdeciderapp.model.Ingredient
import com.example.dinnerdeciderapp.model.Meal
import com.google.gson.GsonBuilder
import java.io.IOException

class ActivityEditMeal : AppCompatActivity() {

    private lateinit var addIngredientBtn: Button
    private lateinit var saveMealBtn: Button
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

    // TODO: There is a lot of repeated code. Fix this and new meal activity. Superclass with two subclasses?
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        // Uses the same activity as new meal
        setContentView(R.layout.activity_new_edit_meal)

        //Get data from intent
        val meal = intent.getParcelableExtra<Meal>("SelectedMeal")

        //Initialise the recycler view
        ingredientListRV = findViewById(R.id.rv_ingredients)
        ingredientListRV.layoutManager = LinearLayoutManager(this)

        mIngredientListAdapterAddIngredient = AdapterAddIngredient(mOnIngredientClickListener)
        ingredientListRV.adapter = mIngredientListAdapterAddIngredient

        mealImageView = findViewById(R.id.imageView_editImage)

        // Get the required views to assign meal info
        ingredientName = findViewById(R.id.editText_IngrName)
        ingredientQuantity = findViewById(R.id.editText_IngrQuantity)
        mealName = findViewById(R.id.editText_MealName)
        mealMethod = findViewById(R.id.editTextMulti_Method)


        // Add meal name to meal name text view
        if (meal != null) {
            mealName.setText(meal.mealName)

            mealImageView.setImageURI(Uri.parse(meal.mealPhoto))

            // Add ingredients to ingredient recycler view
            if (meal.ingredients != null) {
                for (ingredient in meal.ingredients) {
                    mIngredientListAdapterAddIngredient.addIngredient(ingredient)
                }
            }

            if (!meal.method.isNullOrBlank()) {
                // Add method to method text view
                mealMethod.setText(meal.method)
            }
        }

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
                //Update JSON.
                //Functionality for the save meal button TODO: Add to singleton meal array object?
                val name = mealName.text.toString()
                val method = mealMethod.text.toString()

                //Get data from intent
                val meal = intent.getParcelableExtra<Meal>("SelectedMeal")

                if (name.isNotBlank()) {

                    // Get the index of the meal we're updating by the meal id.
                    val mealArrayIndex = MealArrayObject.singletonMealArray.indexOf(meal)

                    // Create a Meal object of the updated meal
                    val updatedMeal = Meal(
                        meal!!.mealId,
                        name,
                        mIngredientListAdapterAddIngredient.getIngredientList(),
                        method,
                        mealImageString
                    )

                    val gson = GsonBuilder().setPrettyPrinting().create()

                    //Add the new meal to the MealArrayObject array list
                    MealArrayObject.singletonMealArray[mealArrayIndex] = updatedMeal

                    //Convert the array list to json
                    val finalMealListString = gson.toJson(MealArrayObject.singletonMealArray)

                    //Write the final json string to file
                    try {
                        JsonMealData().writeJSONMealData(this, finalMealListString.toByteArray())
                        //Show toast to notify that the meal was added successfully
                        val toast = Toast.makeText(
                            this,
                            "'${updatedMeal.mealName}' Updated Successfully",
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                        //Intent to automatically return to the Manage Meals Activity
                        val intent = Intent(this, ActivityMainActivity::class.java).apply {}
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
