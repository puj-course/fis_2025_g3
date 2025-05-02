package com.example.bogotrash.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.bogotrash.R
import com.example.bogotrash.WasteGuidePagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class WasteGuideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waste_guide)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)

        // Configurar el ViewPager con el adaptador
        val adapter = WasteGuidePagerAdapter(this)
        viewPager.adapter = adapter

        // Conectar TabLayout con ViewPager
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Educación"
                1 -> "Campañas"
                else -> "Educación"
            }
        }.attach()
    }
}