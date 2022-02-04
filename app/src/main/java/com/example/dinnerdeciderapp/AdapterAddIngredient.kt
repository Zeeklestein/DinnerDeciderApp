package com.example.dinnerdeciderapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dinnerdeciderapp.model.Ingredient

class AdapterAddIngredient (
    private val onIngredientClickListener: OnIngredientClickListener,
    private val ingredientList: ArrayList<Ingredient> = ArrayList()
    ) : RecyclerView.Adapter<AdapterAddIngredient.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.fragment_new_ingredient_layout, parent, false)
        val holder = ViewHolder(view)

        //to delete an ingredient in the recycler view
        holder.ingredientDelete.setOnClickListener {
            val position = holder.adapterPosition
            val model = ingredientList[position]
            onIngredientClickListener.onDelete(model)
        }

        return holder
    }

    override fun onBindViewHolder(holder: AdapterAddIngredient.ViewHolder, position: Int) {
        val item = ingredientList[position]

        holder.tvIngrQuantity.text = item.quantity
        holder.tvIngrName.text = item.ingredientName
    }

    override fun getItemCount(): Int {
        return  ingredientList.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvIngrQuantity: TextView = view.findViewById(R.id.tv_IngrQuantity)
        val tvIngrName: TextView = view.findViewById(R.id.tv_IngrName)
        val ingredientDelete: ImageView = view.findViewById(R.id.imgBtn_DeleteIngr)
    }

    //Method to add ingredient to the list
    fun addIngredient(model: Ingredient){
        ingredientList.add(model)
        notifyItemChanged(ingredientList.size)
    }

    //Method to remove an ingredient from the list
    fun removeIngredient(model: Ingredient){
        val position = ingredientList.indexOf(model)
        ingredientList.remove(model)
        notifyItemRemoved(position)
    }

    //Method used to automatically assign an id to an ingredient
    fun getNextItemId(): Int{
        var id = 1
        if(ingredientList.isNotEmpty()){
            id = ingredientList.last().ingredientId + 1
        }
        return id
    }

    //Method to access the ingredients list
    fun getIngredientList(): ArrayList<Ingredient> {
        return ingredientList
    }

}