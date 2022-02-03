package com.example.dinnerdeciderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.dinnerdeciderapp.model.MealModelClass
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)

        val viewPagerAdapter = ViewPagerAdapter(this)
        viewPagerAdapter.addFragment(Home())
        viewPagerAdapter.addFragment(ManageMeals())
        viewPagerAdapter.addFragment(ShoppingList())
        viewPager.adapter = viewPagerAdapter


        TabLayoutMediator(tabLayout, viewPager
        ) { tab, position ->
            when (position) {
                0 -> tab.text = "Home"
                1 -> tab.text = "Manage"
                2 -> tab.text = "List"
        }
        }.attach()

        //Get meal data from JSON file and place in meal array singleton object
        MealArrayObject.singletonMealArray = initMealData()

    }

    private fun initMealData(): ArrayList<MealModelClass>{

        var myMealsList: ArrayList<MealModelClass> = ArrayList()

        try{
            //Get the json meal data into a string
            val jsonString = JsonMealData().getJSONMealData(this, this.filesDir)

            if (!jsonString.isNullOrBlank()) {

                //mealsArray.add(Gson().fromJson(jsonString, MealModelClass::class.java))
                val gson = GsonBuilder().create()
                //val mealList = gson.fromJson(jsonString, MealModelClass::class.java)
                myMealsList = gson.fromJson(jsonString,
                    object : TypeToken<ArrayList<MealModelClass>>() {}.type
                )
            }
        } catch (e: JSONException){
            e.printStackTrace()
        }
        return myMealsList

    }
}