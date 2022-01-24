package com.example.dinnerdeciderapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meals (
    val meals: ArrayList<MealModelClass>
): Parcelable{}

@Parcelize
data class MealModelClass (
    val mealName: String,
    val ingredients: ArrayList<Ingredient>,
    val method: String
): Parcelable

@Parcelize
data class Ingredient(
    val ingredientId: Int = 0,
    val ingredientName: String,
    val quantity: String
): Parcelable