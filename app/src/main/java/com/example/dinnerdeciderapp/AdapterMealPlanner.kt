package com.example.dinnerdeciderapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dinnerdeciderapp.model.MealModelClass

class AdapterMealPlanner(
    private val mealList: ArrayList<MealModelClass> = ArrayList()
    ): RecyclerView.Adapter<AdapterMealPlanner.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): ViewHolder{
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.fragment_meal_planner, parent, false) as View
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val listItem = mealList[position]
        holder.tvMealName.text = listItem.mealName
    }

    override  fun getItemCount(): Int{
        return mealList.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        //TODO: update to include meal image
        val tvMealName: TextView = view.findViewById(R.id.tv_mpfMealName)
        val tvWeekday: TextView = view.findViewById(R.id.tv_mpfWeekday)
    }
}