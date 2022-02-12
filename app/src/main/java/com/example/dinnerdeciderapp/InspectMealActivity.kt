package com.example.dinnerdeciderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.dinnerdeciderapp.model.Meal

class InspectMealActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspect_meal)

        val mealName: TextView = findViewById(R.id.tv_inspMeal_MealName)

        val receivedIntent = intent.getParcelableExtra<Meal>("SelectedMeal")
        mealName.text = receivedIntent?.mealName
    }
}