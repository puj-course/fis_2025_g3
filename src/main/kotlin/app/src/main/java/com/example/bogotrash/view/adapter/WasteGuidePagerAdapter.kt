package com.example.bogotrash.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bogotrash.view.fragment.EducationFragment
import com.example.bogotrash.view.fragment.CampaignFragment

class WasteGuidePagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> EducationFragment()
            1 -> CampaignFragment()
            else -> EducationFragment()
        }
    }
}