package com.example.dinnerdeciderapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dinnerdeciderapp.model.MealModelClass

class ListMealsAdapter(
    private val context: Context,
    private val mealList: ArrayList<MealModelClass> = ArrayList()
    ) : RecyclerView.Adapter<ListMealsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(
            R.layout.meal_item_layout, parent, false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mealList[position]

        holder.tvMealName.text = item.mealName
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvMealName: TextView = view.findViewById(R.id.tv_IngrName)
    }

    //Method to access the Meal list
    fun getMealList(): ArrayList<MealModelClass> {
        return mealList
    }

}

