package com.example.dinnerdeciderapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class AdapterViewPager(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    private val tabFragmentList = ArrayList<Fragment>()

    override fun getItemCount(): Int = tabFragmentList.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentList[position]
    }

    //Function to add new fragments to the list
    fun addFragmentToList(fragment: Fragment) {
        tabFragmentList.add(fragment)
    }
}