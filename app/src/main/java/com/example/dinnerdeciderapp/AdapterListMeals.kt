package com.example.dinnerdeciderapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.dinnerdeciderapp.model.Meal

class AdapterListMeals(
    private val mealList: ArrayList<Meal> = ArrayList()
    ) : RecyclerView.Adapter<AdapterListMeals.ViewHolder>() {

    private lateinit var holder: ViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.fragment_meal_item_layout, parent, false) as View
        holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = mealList[position]
        holder.bind(listItem)
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        val tvMealName: TextView = view.findViewById(R.id.tv_IngrName)

        fun bind (meal: Meal){
            tvMealName.text = meal.mealName

            this.view.setOnClickListener {
                val context = holder.view.context
                val intent = Intent(context, InspectMealActivity::class.java).apply {
                     putExtra("SelectedMeal", meal)
                }
                context.startActivity(intent)
            }
        }
    }
}

