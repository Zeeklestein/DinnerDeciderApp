package com.example.dinnerdeciderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        mealName.text = receivedIntent?.mealName

        //Set up ingredients as a string array
        val ingredientStringArray: ArrayList<String> = ArrayList()
        if (!receivedIntent?.ingredients.isNullOrEmpty()){
            for (ingredient in receivedIntent?.ingredients!!){
                ingredientStringArray.add("${ingredient.quantity} ${ingredient.ingredientName}")
            }

            //Send ingredient data to ingredient tab fragment
            //val fragmentManager = supportFragmentManager
            //val fragmentTransaction = fragmentManager.beginTransaction()
            val fragment = TabInspectIngredients()
            val bundle = Bundle()
            bundle.putStringArrayList("ingreList", ingredientStringArray)
            fragment.arguments = bundle

        }



    }
}