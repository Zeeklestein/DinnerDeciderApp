package com.example.dinnerdeciderapp

import android.content.Context
import android.widget.Toast
import com.example.dinnerdeciderapp.model.Meal
import kotlin.random.Random

class MealRandomiser(
    private val context: Context
    ){

    private val selectedMealArray: ArrayList<Meal> = ArrayList()

    //This function returns an arraylist of 7 random meals from the MealArrayObject
    fun getRandomMeals(): ArrayList<Meal>{
        /*Check if Meal Array Object contains at least 7 meals. Else use toast to tell user to
        add more meals.*/
        if(MealArrayObject.singletonMealArray.size >= 7) {
            //This is repeated For each day of the week
            for (day in 1..7){
                var isLooping = true
                while (isLooping){
                    //Random number from 0 to array size is used as the array index to get a random meal
                    val randomIndex = Random.nextInt(0, MealArrayObject.singletonMealArray.size)
                    if(!selectedMealArray.contains(MealArrayObject.singletonMealArray[randomIndex])){
                        //The randomised meal is added to the selected meals array
                        selectedMealArray.add(MealArrayObject.singletonMealArray[randomIndex])
                        isLooping = false
                    }
                }
            }
        } else{
            val toast = Toast.makeText(context, "Need at least 7 meals to use Randomiser!", Toast.LENGTH_SHORT)
            toast.show()
        }
        return selectedMealArray
    }




}