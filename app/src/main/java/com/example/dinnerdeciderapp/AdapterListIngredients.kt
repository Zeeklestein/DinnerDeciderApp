package com.example.dinnerdeciderapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dinnerdeciderapp.model.Ingredient

class AdapterListIngredients(
    private val itemList: ArrayList<String>
): RecyclerView.Adapter<AdapterListIngredients.ViewHolder>() {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.text_row_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvIngredient.text = itemList[position]
    }

    override fun getItemCount() = itemList.size

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        val tvIngredient: TextView = view.findViewById(R.id.tv_textItem)
    }

}