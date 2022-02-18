package com.example.dinnerdeciderapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TabInspectIngredients : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tab_inspect_ingredients, container, false)

        val bundle = arguments
        val message = bundle?.getStringArrayList("ingreList")


        val rvIngredients = view.findViewById<RecyclerView>(R.id.rv_tab_ingredients)
        rvIngredients.adapter = AdapterListIngredients(message as ArrayList<String>) //Getting null pointer exception

        rvIngredients.layoutManager = LinearLayoutManager(view.context)

        return view
    }
}