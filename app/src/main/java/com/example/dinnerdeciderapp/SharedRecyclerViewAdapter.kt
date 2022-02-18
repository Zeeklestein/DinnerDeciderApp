package com.example.dinnerdeciderapp

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SharedRecyclerViewAdapter
    /*private val itemList: an array list
    * private val layoutRef: a reference to the layout to be inflated
    * */ (): RecyclerView.Adapter<SharedRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SharedRecyclerViewAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: SharedRecyclerViewAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){

    }


}