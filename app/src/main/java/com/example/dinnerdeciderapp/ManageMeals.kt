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
import com.example.dinnerdeciderapp.model.MealModelClass
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONException

class ManageMeals : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_manage_meals, container, false)
        val newMealButton = view.findViewById<Button>(R.id.btn_NewMeal)

        try{
            //Get the json meal data into a string
            val jsonString = JsonMealData().getJSONMealData(view.context, view.context.filesDir)

            if (!jsonString.isNullOrBlank()){

                //mealsArray.add(Gson().fromJson(jsonString, MealModelClass::class.java))
                val gson = GsonBuilder().create()
                //val mealList = gson.fromJson(jsonString, MealModelClass::class.java)
                val mealList = gson.fromJson<ArrayList<MealModelClass>>(jsonString,
                    object : TypeToken<ArrayList<MealModelClass>>(){}.type)

                val rvMealList = view.findViewById<RecyclerView>(R.id.rv_MealList)


                rvMealList.adapter = ListMealsAdapter(mealList)
                //Add meal objects to the recycler view
                rvMealList.layoutManager = LinearLayoutManager(view.context)
            }
        } catch (e: JSONException){
            e.printStackTrace()
        }


        //Listener for the new meal button. Starts the new meal activity.
        newMealButton.setOnClickListener {
            val intent = Intent (view.context, NewMealActivity::class.java).apply{}
            startActivity(intent)
        }

        // Inflate the layout for this fragment
        return view
    }


}