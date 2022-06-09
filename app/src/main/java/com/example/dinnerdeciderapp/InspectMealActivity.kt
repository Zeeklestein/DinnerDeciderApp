package com.example.dinnerdeciderapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.viewModels
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.dinnerdeciderapp.model.Meal
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.GsonBuilder
import java.io.IOException

class InspectMealActivity : AppCompatActivity() {

    private lateinit var deleteMealBtn: ImageButton
    private lateinit var editMealBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspect_meal)

        //Tab layout and view pager functionality
        val tabLayout = findViewById<TabLayout>(R.id.tabs_inspMeal)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager_inspMeal)

        val viewPagerAdapter = AdapterViewPager(this)
        viewPagerAdapter.addFragmentToList(TabInspectIngredients())
        viewPagerAdapter.addFragmentToList(TabInspectMethod())
        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager){
            tab, position ->
            when (position){
                0 -> tab.text = "Ingredients"
                1 -> tab.text = "Method"
            }
        }.attach()

        val mealName: TextView = findViewById(R.id.tv_inspMeal_MealName)

        //Get data from intent
        val receivedIntent = intent.getParcelableExtra<Meal>("SelectedMeal")

        val viewModel by viewModels<SharedViewModel>()

        if (receivedIntent != null) {
            mealName.text = receivedIntent.mealName
            viewModel.setInspectedMeal(receivedIntent)
        }

        /* TODO:("Allow the user to edit and delete existing meals.")*/

        // Functionality for the edit meal button
        editMealBtn = findViewById(R.id.btn_inspMeal_Edit)
        editMealBtn.setOnClickListener {

            // TODO: Add edit functionality


        }


        // Functionality for the delete meal button TODO: Add to singleton meal array object?
        deleteMealBtn = findViewById(R.id.btn_inspMeal_Delete)
        deleteMealBtn.setOnClickListener {

            if (receivedIntent != null) {

                // Delete meal from the singleton meal array object
                MealArrayObject.deleteMealItem(receivedIntent)

                val gson = GsonBuilder().setPrettyPrinting().create()

                //Convert the array list to json
                val finalMealListString = gson.toJson(MealArrayObject.singletonMealArray)

                //Write the final json string to file
                // TODO: Add a confirm deletion message box
                try{
                    JsonMealData().writeJSONMealData(this, finalMealListString.toByteArray())
                    //Show toast to notify that the meal was removed successfully
                    val toast = Toast.makeText(this, "'${receivedIntent.mealName}' Removed Successfully", Toast.LENGTH_SHORT)
                    toast.show()
                    //Intent to automatically return to the Main Activity
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