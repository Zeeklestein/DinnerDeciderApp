package com.example.dinnerdeciderapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class TabHome : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        //Functionality for the randomise button
        val randomiseButton = view.findViewById<Button>(R.id.btn_randomise)
        val randomiser = MealRandomiser(view.context)
        randomiseButton.setOnClickListener {
            val randomMealsArray = randomiser.getRandomMeals()
        }

        return view
    }
}