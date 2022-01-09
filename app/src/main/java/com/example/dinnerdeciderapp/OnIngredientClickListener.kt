package com.example.dinnerdeciderapp

import com.example.dinnerdeciderapp.model.Ingredient
import com.example.dinnerdeciderapp.model.MealModelClass

interface OnIngredientClickListener {

    //fun onUpdate(position: Int, model: Ingredient)

    fun onDelete(model: Ingredient)
}