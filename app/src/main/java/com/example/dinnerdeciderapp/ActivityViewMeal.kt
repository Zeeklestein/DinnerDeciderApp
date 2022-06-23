package com.example.dinnerdeciderapp

import android.content.DialogInterface
import android.content.Intent
import android.media.metrics.Event
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.ViewPager2
import com.example.dinnerdeciderapp.model.Meal
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.GsonBuilder
import java.io.IOException

class ActivityViewMeal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_meal)

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
    }

    // Set menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.meal_options_menu, menu)
        return true
    }

    // TODO: Add edit and delete to singleton meal array object?

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val receivedIntent = intent.getParcelableExtra<Meal>("SelectedMeal")

        if(receivedIntent != null) {
            when (item.itemId) {

                // Functionality for edit meal menu selection
                R.id.viewMeal_edit -> {

                    val intent = Intent(this, ActivityEditMeal::class.java).apply {
                        putExtra("SelectedMeal", receivedIntent)
                    }
                    startActivity(intent)
                }

                // Functionality for delete meal menu selection
                R.id.viewMeal_delete -> {

                    deleteEvent(receivedIntent)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    // Function to show dialog box and delete meal from my meals
    private fun deleteEvent( meal: Meal) {

        val builder = AlertDialog.Builder(this)

        builder.setTitle("Delete Meal")
        builder.setMessage("Are you sure you want to delete this meal?")

        builder.setPositiveButton("Yes"){ dialog, id ->

            // Delete meal from the singleton meal array object
            MealArrayObject.deleteMealItem(meal)

            val gson = GsonBuilder().setPrettyPrinting().create()

            //Convert the array list to json
            val finalMealListString = gson.toJson(MealArrayObject.singletonMealArray)

            //Write the final json string to file
            // TODO: Add a confirm deletion message box
            try{
                JsonMealData().writeJSONMealData(this, finalMealListString.toByteArray())
                //Show toast to notify that the meal was removed successfully
                val toast = Toast.makeText(this, "'${meal.mealName}' Removed Successfully", Toast.LENGTH_SHORT)
                toast.show()
                //Intent to automatically return to the Main Activity
                val intent = Intent (this, ActivityMainActivity::class.java).apply{}
                startActivity(intent)
            }
            catch (ex: IOException){
                ex.printStackTrace()
            }

            dialog.cancel()
        }

        builder.setNegativeButton("No") {dialog, id ->
            dialog.cancel()
        }

        // Show dialog
        val alert = builder.create()
        alert.show()
    }
}