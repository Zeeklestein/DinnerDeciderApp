package com.example.dinnerdeciderapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dinnerdeciderapp.model.Ingredient

class AddIngredientAdapter (
    private val mContext: Context,
    private val mOnIngredientClickListener: OnIngredientClickListener,
    private val mIngredientList: ArrayList<Ingredient> = ArrayList()
    ) : RecyclerView.Adapter<AddIngredientAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.new_ingredient_layout, parent, false)
        val holder = ViewHolder(view)

        //to delete an ingredient in the recycler view
        holder.ingredientDelete.setOnClickListener {
            val position = holder.adapterPosition
            val model = mIngredientList[position]
            mOnIngredientClickListener.onDelete(model)
        }

        return holder
    }

    override fun onBindViewHolder(holder: AddIngredientAdapter.ViewHolder, position: Int) {
        val item = mIngredientList[position]

        holder.tvIngrQuantity.text = item.quantity
        holder.tvIngrName.text = item.ingredientName
    }

    override fun getItemCount(): Int {
        return  mIngredientList.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvIngrQuantity: TextView = view.findViewById(R.id.tv_IngrQuantity)
        val tvIngrName: TextView = view.findViewById(R.id.tv_IngrName)
        val ingredientDelete: ImageView = view.findViewById(R.id.imgBtn_DeleteIngr)
    }

    //Method to add ingredient to the list
    fun addIngredient(model: Ingredient){
        mIngredientList.add(model)
        notifyItemChanged(mIngredientList.size)
    }

    //Method to remove an ingredient from the list
    fun removeIngredient(model: Ingredient){
        val position = mIngredientList.indexOf(model)
        mIngredientList.remove(model)
        notifyItemRemoved(position)
    }

    //Method used to automatically assign an id to an ingredient
    fun getNextItemId(): Int{
        var id = 1
        if(mIngredientList.isNotEmpty()){
            id = mIngredientList.last().ingredientId + 1
        }
        return id
    }

}