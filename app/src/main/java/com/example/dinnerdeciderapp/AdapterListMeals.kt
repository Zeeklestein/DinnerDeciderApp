package com.example.dinnerdeciderapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dinnerdeciderapp.model.MealModelClass

class AdapterListMeals(
    private val mealList: ArrayList<MealModelClass> = ArrayList()
    ) : RecyclerView.Adapter<AdapterListMeals.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.fragment_meal_item_layout, parent, false) as View
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = mealList[position]
        holder.tvMealName.text = listItem.mealName
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvMealName: TextView = view.findViewById(R.id.tv_IngrName)
    }
}

