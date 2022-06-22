package com.example.dinnerdeciderapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dinnerdeciderapp.model.Meal
import com.google.gson.GsonBuilder
import java.io.IOException

class TabPlanner : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_tab_home, container, false)

        var randomMealsArray: ArrayList<Meal> = ArrayList()

        val rvMealPlanner = view.findViewById<RecyclerView>(R.id.rv_mealPlanner)
        val mealPlannerAdapter = AdapterMealPlanner()
        rvMealPlanner.adapter = mealPlannerAdapter
        rvMealPlanner.layoutManager = LinearLayoutManager(view.context)

        // First, check if planner file exists, if so, load the meal plan data from that
        if (JsonMealData().mealPlanFileExists(this.requireContext().filesDir)){

            // Get meal plan data from file, apply it to the meal planner recycler view
            mealPlannerAdapter.setMealList(JsonMealData().getMealPlanData(this.requireActivity(), this.requireActivity().filesDir))
        }

        //Functionality for the randomise button
        val randomiseButton = view.findViewById<Button>(R.id.btn_randomise)
        val randomiser = MealRandomiser(view.context)
        randomiseButton.setOnClickListener {
            randomMealsArray.clear()
            randomMealsArray = randomiser.getRandomMeals()

            //Notify the recycler view to add the randomised meal data.
            mealPlannerAdapter.setMealList(randomMealsArray)
            //rvMealPlanner.adapter.notifyItemChanged(randomMealsArray.size)

            // Save randomised meal plan in a local JSON file
            val gson = GsonBuilder().setPrettyPrinting().create()
            //Convert the array list to json
            val mealPlanString = gson.toJson(randomMealsArray)

            //Write the json string to file
            try{
                JsonMealData().writeMealPlanData(this.requireContext(), mealPlanString.toByteArray())
            }
            catch (ex: IOException){
                ex.printStackTrace()
            }
        }

        return view
    }
}