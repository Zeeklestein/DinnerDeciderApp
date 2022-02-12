package com.example.dinnerdeciderapp

import com.example.dinnerdeciderapp.model.Ingredient
import com.example.dinnerdeciderapp.model.Meal

interface OnItemClickListener {

    //fun onUpdate(position: Int, model: Ingredient)
    //fun onClick(model: Meal)

    fun onDelete(model: Ingredient)
}