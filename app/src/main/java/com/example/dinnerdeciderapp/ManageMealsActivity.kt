package com.example.dinnerdeciderapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dinnerdeciderapp.model.MealModelClass
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONException

class ManageMealsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_meals)

        val newMealButton = findViewById<Button>(R.id.btn_NewMeal)

        try{
            //Get the json meal data into a string
            val jsonString = JsonMealData().getJSONMealData(this, this.filesDir)

            if (!jsonString.isNullOrBlank()){

                //mealsArray.add(Gson().fromJson(jsonString, MealModelClass::class.java))
                val gson = GsonBuilder().create()
                //val mealList = gson.fromJson(jsonString, MealModelClass::class.java)
                val mealList = gson.fromJson<ArrayList<MealModelClass>>(jsonString,
                    object :TypeToken<ArrayList<MealModelClass>>(){}.type)

                val rvMealList = findViewById<RecyclerView>(R.id.rv_MealList)


                rvMealList.adapter = ListMealsAdapter(mealList)
                //Add meal objects to the recycler view
                rvMealList.layoutManager = LinearLayoutManager(this)
            }
        } catch (e: JSONException){
            e.printStackTrace()
        }


        //Listener for the new meal button. Starts the new meal activity.
        newMealButton.setOnClickListener {
            val intent = Intent (this, NewMealActivity::class.java).apply{}
            startActivity(intent)
        }
    }
}


