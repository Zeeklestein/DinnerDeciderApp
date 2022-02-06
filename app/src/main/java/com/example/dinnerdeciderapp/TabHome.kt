package com.example.dinnerdeciderapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dinnerdeciderapp.model.MealModelClass

class TabHome : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_tab_home, container, false)

        var randomMealsArray: ArrayList<MealModelClass> = ArrayList()

        val rvMealPlanner = view.findViewById<RecyclerView>(R.id.rv_mealPlanner)
        val mealPlannerAdapter = AdapterMealPlanner()
        rvMealPlanner.adapter = mealPlannerAdapter
        rvMealPlanner.layoutManager = LinearLayoutManager(view.context)


        //Functionality for the randomise button
        val randomiseButton = view.findViewById<Button>(R.id.btn_randomise)
        val randomiser = MealRandomiser(view.context)
        randomiseButton.setOnClickListener {
            randomMealsArray.clear()
            randomMealsArray = randomiser.getRandomMeals()

            //Notify the recycler view to add the randomised meal data.
            mealPlannerAdapter.setMealList(randomMealsArray)
            //rvMealPlanner.adapter.notifyItemChanged(randomMealsArray.size)
        }

        return view
    }
}