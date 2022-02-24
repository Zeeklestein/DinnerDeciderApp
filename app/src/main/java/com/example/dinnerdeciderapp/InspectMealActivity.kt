package com.example.dinnerdeciderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.example.dinnerdeciderapp.model.Meal
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class InspectMealActivity : AppCompatActivity() {

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

        /*TODO: Get the Ingredients to the ingredient tab fragment
           Get the method to the method tab fragment*/
        if (receivedIntent != null) {
            mealName.text = receivedIntent.mealName
            viewModel.setInspectedMeal(receivedIntent)
        }



    }
}