package com.example.dinnerdeciderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)

        val viewPagerAdapter = ViewPagerAdapter(this)
        viewPagerAdapter.addFragment(Home())
        viewPagerAdapter.addFragment(ManageMeals())
        viewPagerAdapter.addFragment(ShoppingList())
        viewPager.adapter = viewPagerAdapter


        TabLayoutMediator(tabLayout, viewPager
        ) { tab, position ->
            when (position) {
                0 -> tab.text = "Home"
                1 -> tab.text = "Manage"
                2 -> tab.text = "List"
        }
        }.attach()

    }
}