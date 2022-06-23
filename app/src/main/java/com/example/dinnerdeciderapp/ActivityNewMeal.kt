package com.example.dinnerdeciderapp

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dinnerdeciderapp.model.Ingredient
import com.example.dinnerdeciderapp.model.Meal
import com.google.gson.GsonBuilder
import java.io.IOException
import java.util.jar.Manifest

class ActivityNewMeal : AppCompatActivity() {

    private lateinit var addIngredientBtn: Button
    private lateinit var ingredientName: EditText
    private lateinit var ingredientQuantity: EditText
    private lateinit var mealName: EditText
    private lateinit var mealMethod: EditText
    private lateinit var mealImage: ImageView

    private lateinit var image: Bitmap

    private lateinit var ingredientListRV: RecyclerView
    private lateinit var mIngredientListAdapterAddIngredient: AdapterAddIngredient

    companion object {
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE = 2
    }

    private val mOnIngredientClickListener = object : OnItemClickListener {

        override fun onDelete(model: Ingredient) {
            mIngredientListAdapterAddIngredient.removeIngredient(model)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_edit_meal)

        //supportActionBar?.title = "New Meal"

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

        val imgBtnCamera = findViewById<ImageButton>(R.id.imgBtn_camera)
        imgBtnCamera.setOnClickListener {

            // Check if we have permission
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE) //TODO: Fix
            } else {
                // Request Permission
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
            }

        }

    }

    // Function to request permission from the user to use the camera
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == CAMERA_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE) //TODO: Fix
            } else {
                Toast.makeText(this, "Permission required to use camera.", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Function to get and save the received image TODO: Fix
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == CAMERA_REQUEST_CODE){

                // Get the image
                image = data!!.extras!!.get("data") as Bitmap

                // Apply the image to the image view
                mealImage = findViewById(R.id.imageView_editImage)
                mealImage.setImageBitmap(image)

            }
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

                    //Todo: save image to in app files and reference in JSON
                    val newMeal = Meal(
                        mealId,
                        name,
                        mIngredientListAdapterAddIngredient.getIngredientList(),
                        method
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