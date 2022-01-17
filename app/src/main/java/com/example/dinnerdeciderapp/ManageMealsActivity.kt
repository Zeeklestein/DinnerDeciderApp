package com.example.dinnerdeciderapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dinnerdeciderapp.model.Meals
import com.google.gson.Gson
import org.json.JSONException
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class ManageMealsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_meals)

        val newMealButton = findViewById<Button>(R.id.btn_NewMeal)

        try{
            val jsonString = getJSONMealData()!!

            if (jsonString.isNotBlank()){
                val meals = Gson().fromJson(jsonString, Meals::class.java)
                val rvMealList = findViewById<RecyclerView>(R.id.rv_MealList)

                rvMealList.layoutManager = LinearLayoutManager(this)
                val itemAdapter = ListMealsAdapter(this, meals.meals)
                rvMealList.adapter = itemAdapter
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

    private fun getJSONMealData(): String? {

        var json: String? = null
        val myFileName = "myMeals.json"
        val file = File(this.filesDir, myFileName)

            try {
                //Check it the file does not exist
                if(!file.exists()){
                    //Creates the file if not exists
                    this.openFileOutput(myFileName, Context.MODE_PRIVATE).use{
                        it.write("".toByteArray())
                    }
                } else {
                    //Else, opens the file and reads the text
                    this.openFileInput(myFileName).use { stream ->
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


}


