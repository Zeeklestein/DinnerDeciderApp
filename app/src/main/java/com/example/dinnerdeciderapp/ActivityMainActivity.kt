package com.example.dinnerdeciderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class ActivityMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabLayout = findViewById<TabLayout>(R.id.tabs_mainActivity)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager_mainActivity)

        //Set up view pager adapter and add the fragments to its array
        val viewPagerAdapter = AdapterViewPager(this)
        viewPagerAdapter.addFragmentToList(TabPlanner())
        viewPagerAdapter.addFragmentToList(TabManageMeals())
        viewPagerAdapter.addFragmentToList(TabShoppingList())
        viewPager.adapter = viewPagerAdapter

        //Set up tab layout tabs_mainActivity
        TabLayoutMediator(tabLayout, viewPager
        ) { tab, position ->
            when (position) {
                0 -> tab.text = "Planner"
                1 -> tab.text = "My Meals"
                2 -> tab.text = "Shopping List"
        }
        }.attach()

        //Get meal data from JSON file and place in meal array singleton object
        //MealArrayObject.singletonMealArray = initMealData()
        MealArrayObject.initMealData(this)
    }
}