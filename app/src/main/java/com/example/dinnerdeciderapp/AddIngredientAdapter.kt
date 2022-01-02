package com.example.dinnerdeciderapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dinnerdeciderapp.model.Ingredient
import com.example.dinnerdeciderapp.model.MealModelClass

class AddIngredientAdapter (private val context: Context, private val items: List<Ingredient>) : RecyclerView.Adapter<AddIngredientAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(
            R.layout.new_ingredient_layout, parent, false
        ))
    }

    override fun onBindViewHolder(holder: AddIngredientAdapter.ViewHolder, position: Int) {
        val item = items[position]

        holder.tvIngrQuantity.text = item.quantity
        holder.tvIngrName.text = item.ingredientName
    }

    override fun getItemCount(): Int {
        return  items.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvIngrQuantity: TextView = view.findViewById(R.id.tv_IngrQuantity)
        val tvIngrName: TextView = view.findViewById(R.id.tv_IngrName)
    }
}