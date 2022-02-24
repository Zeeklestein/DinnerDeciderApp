package com.example.dinnerdeciderapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dinnerdeciderapp.model.Meal

class SharedViewModel : ViewModel() {

    private val _inspectedMeal : MutableLiveData<Meal> = MutableLiveData()
    val inspectedMeal: LiveData<Meal> = _inspectedMeal


    fun setInspectedMeal(meal: Meal){
        _inspectedMeal.value = meal
        Log.i("inspectedMeal", "Meal: ${_inspectedMeal.value}")
    }

}