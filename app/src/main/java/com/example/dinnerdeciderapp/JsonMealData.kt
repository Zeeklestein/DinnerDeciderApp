package com.example.dinnerdeciderapp

import android.content.Context
import android.util.Log
import com.example.dinnerdeciderapp.model.Meal
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.IOException

class JsonMealData {

    private val myMealsFile = "myMeals.json"
    private val myMealPlanFile = "myMealPlan.json"

    fun getJSONMealData(context: Context, filesDir: File): String? {

        var json: String? = null
        val file = File(filesDir, myMealsFile)

        try {
            //Check it the file does not exist
            if(!file.exists()){
                //Creates the file if not exists
                context.openFileOutput(myMealsFile, Context.MODE_PRIVATE).use{
                    it.write("".toByteArray())
                }
            } else {
                //Else, opens the file and reads the text
                context.openFileInput(myMealsFile).use { stream ->
                    json = stream.bufferedReader().use {
                        it.readText()
                    }
                }
            }
        } catch (ex: IOException){
            ex.printStackTrace()
            return null
        }
        return json
    }


    // Function for writing meal data to JSON file
    fun writeJSONMealData(context: Context, byteArray: ByteArray){
        context.openFileOutput(myMealsFile, Context.MODE_PRIVATE).use{
            it.write(byteArray)
        }
    }

    // Function for writing meal plan data to JSON file
    fun writeMealPlanData(context: Context, byteArray: ByteArray){
        context.openFileOutput(myMealPlanFile, Context.MODE_PRIVATE).use{
            it.write(byteArray)
        }
    }

    // Function to check if myMealPlan.json file exists
    fun mealPlanFileExists(filesDir: File): Boolean {
        val file = File( filesDir, myMealPlanFile)
        return file.exists()
    }

    fun getMealPlanData(context: Context, filesDir: File): ArrayList<Meal> {

        var json: String? = null
        var existingMealPlanData = arrayListOf<Meal>()
        val file = File(filesDir, myMealPlanFile)

        try {
            // Opens the file and reads the text
            context.openFileInput(myMealPlanFile).use { stream ->
                json = stream.bufferedReader().use {
                    it.readText()
                }
            }

            if (!json.isNullOrBlank()) {

                val gson = GsonBuilder().create()
                existingMealPlanData = gson.fromJson(json,
                    object : TypeToken<ArrayList<Meal>>() {}.type
                )
            }

        } catch (ex: IOException){
            ex.printStackTrace()
        }
        return existingMealPlanData
    }
}