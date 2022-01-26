package com.example.dinnerdeciderapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(mFragmentActivity: FragmentActivity): FragmentStateAdapter(mFragmentActivity) {

    private val mFragmentList = ArrayList<Fragment>()

    override fun getItemCount(): Int = mFragmentList.size

    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }

    //Function to add new fragments to the list
    fun addFragment(fragment: Fragment) {
        mFragmentList.add(fragment)
    }
}