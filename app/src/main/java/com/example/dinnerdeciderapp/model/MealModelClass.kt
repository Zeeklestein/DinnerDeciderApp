package com.example.dinnerdeciderapp.model

data class Meals (
    val meals: MutableList<MealModelClass>
)

data class MealModelClass (
    val mealName: String,
    val ingredients: MutableList<Ingredient>,
    val method: String
)

data class Ingredient(
    val ingredientName: String,
    val quantity: String
)