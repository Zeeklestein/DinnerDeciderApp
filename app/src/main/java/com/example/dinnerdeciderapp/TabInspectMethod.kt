package com.example.dinnerdeciderapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class TabInspectMethod : Fragment() {

    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        val method = viewModel.inspectedMeal.value?.method

        val view = inflater.inflate(R.layout.fragment_tab_inspect_method, container, false)
        val tvMethod = view.findViewById<TextView>(R.id.tv_inspect_method)

        if (method.isNullOrBlank()){
            tvMethod.text = "No Method Added."
        } else {
            tvMethod.text = method
        }


        return view
    }
}