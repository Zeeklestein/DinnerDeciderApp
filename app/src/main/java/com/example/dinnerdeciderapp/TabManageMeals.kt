package com.example.dinnerdeciderapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/*Functionality for the Manage Meals tab. Allows the uses to view their meals and add new meals.*/

class TabManageMeals : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tab_manage_meals, container, false)
        val newMealButton = view.findViewById<Button>(R.id.btn_NewMeal)

        val rvMealList = view.findViewById<RecyclerView>(R.id.rv_MealList)

        //Adapter uses the MealArrayObject singleton's array of meals
        rvMealList.adapter = AdapterListMeals(MealArrayObject.singletonMealArray)
        //Add meal objects to the recycler view
        rvMealList.layoutManager = LinearLayoutManager(view.context)

        //Listener for the new meal button. Starts the new meal activity.
        newMealButton.setOnClickListener {
            val intent = Intent (view.context, ActivityNewMeal::class.java).apply{}
            startActivity(intent)
        }

        return view
    }


}