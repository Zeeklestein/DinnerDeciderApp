package com.example.dinnerdeciderapp

import android.content.Context
import com.example.dinnerdeciderapp.model.Meal
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONException

//This is a singleton object that contains an array of meals
object MealArrayObject {

    var singletonMealArray: ArrayList<Meal> = ArrayList()

    // Method to initialise meal data from JSON file to the singleton array
    fun initMealData(context: Context){

        try{
            //Get the json meal data into a string
            val jsonString = JsonMealData().getJSONMealData(context, context.filesDir)

            if (!jsonString.isNullOrBlank()) {

                //mealsArray.add(Gson().fromJson(jsonString, Meal::class.java))
                val gson = GsonBuilder().create()
                //val mealList = gson.fromJson(jsonString, Meal::class.java)
                singletonMealArray = gson.fromJson(jsonString,
                    object : TypeToken<ArrayList<Meal>>() {}.type
                )
            }
        } catch (e: JSONException){
            e.printStackTrace()
        }
    }

    fun deleteMealItem(mealItem: Meal){

        for( meal in singletonMealArray){
            if(mealItem == meal) {
                singletonMealArray.remove(mealItem)
            }
        }
    }
}