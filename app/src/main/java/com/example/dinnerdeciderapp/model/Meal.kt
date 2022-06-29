package com.example.dinnerdeciderapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meal (
    val mealId: Int = 0,
    val mealName: String?,
    val ingredients: ArrayList<Ingredient>?,
    val method: String?,
    val mealPhoto: String?
): Parcelable

@Parcelize
data class Ingredient(
    val ingredientId: Int = 0,
    val ingredientName: String?,
    val quantity: String?
): Parcelable