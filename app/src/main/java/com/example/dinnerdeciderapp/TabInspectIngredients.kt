package com.example.dinnerdeciderapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TabInspectIngredients : Fragment() {

    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        val ingredients = viewModel.inspectedMeal.value?.ingredients

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tab_inspect_ingredients, container, false)
        val rvIngredients = view.findViewById<RecyclerView>(R.id.rv_tab_ingredients)

        val ingredientsList: ArrayList<String> = ArrayList()

        if (ingredients != null) {
            for (ingredient in ingredients){
                ingredientsList.add("${ingredient.quantity} ${ingredient.ingredientName}")
            }
        } else {
            ingredientsList.add("Empty")
        }

        rvIngredients.adapter = AdapterListIngredients(ingredientsList)
        rvIngredients.layoutManager = LinearLayoutManager(view.context)

        return view
    }
}