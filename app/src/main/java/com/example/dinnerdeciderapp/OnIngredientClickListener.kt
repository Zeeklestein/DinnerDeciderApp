package com.example.dinnerdeciderapp

import com.example.dinnerdeciderapp.model.Ingredient

interface OnIngredientClickListener {

    //fun onUpdate(position: Int, model: Ingredient)

    fun onDelete(model: Ingredient)
}