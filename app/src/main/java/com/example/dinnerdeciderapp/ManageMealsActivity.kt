package com.example.dinnerdeciderapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dinnerdeciderapp.model.Meals
import com.google.gson.Gson
import org.json.JSONException
import java.io.IOException
import java.nio.charset.Charset

class ManageMealsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_meals)

        val newMealButton = findViewById<Button>(R.id.btn_NewMeal)

        try{
            val jsonString = getJSONMealData()!!
            val meals = Gson().fromJson(jsonString, Meals::class.java)
            val rvMealList = findViewById<RecyclerView>(R.id.rv_MealList)

            rvMealList.layoutManager = LinearLayoutManager(this)
            val itemAdapter = ListMealsAdapter(this, meals.meals)
            rvMealList.adapter = itemAdapter

        } catch (e: JSONException){
            e.printStackTrace()
        }

        //Listener for the new meal button. Starts the new meal activity.
        newMealButton.setOnClickListener {
            val intent = Intent (this, NewMealActivity::class.java).apply{}
            startActivity(intent)
        }
    }


    private fun getJSONMealData(): String? {

        var json: String? = null
        val charset: Charset = Charsets.UTF_8

        try {
            val mealJSONFile = assets.open("myMeals.json")
            val size = mealJSONFile.available()
            val buffer = ByteArray(size)
            mealJSONFile.read(buffer)
            mealJSONFile.close()
            json = String(buffer, charset)
        } catch (ex: IOException){
            ex.printStackTrace()
            //TODO("Add message if no meals exist in file")
            return null
        }
        return json
    }

}

