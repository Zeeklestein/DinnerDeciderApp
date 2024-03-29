package com.example.dinnerdeciderapp

import android.content.Context
import com.example.dinnerdeciderapp.model.Meal
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONException

//This is a singleton object that contains an array of meals
object MealArrayObject {

    var singletonMealArray: ArrayList<Meal> = ArrayList()

    // Used to assign the next meal id to a new meal
    fun getNextItemId(): Int{
        var id = 1
        if(singletonMealArray.isNotEmpty()){
            id = singletonMealArray.last().mealId + 1
        }
        return id
    }

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

        // To avoid 'ConcurrentModificationException', an iterator needs to be used
        val iterator = singletonMealArray.iterator()

        // Iterate through the array list and delete the passed in meal
        while(iterator.hasNext()){
            val meal = iterator.next()
            if(meal.mealId == mealItem.mealId){
                iterator.remove()
            }
        }
    }
}