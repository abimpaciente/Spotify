package com.example.spotify.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.spotify.MediaFragmentLayout

class SliderMediaAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                MediaFragmentLayout("rock")
            }
            1 -> {
                MediaFragmentLayout("classick")
            }
            2 -> {
                MediaFragmentLayout("pop")
            }
            else -> {
                MediaFragmentLayout("rock")
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "Rock"
            }
            1 -> {
                return "Classic"
            }
            2 -> {
                return "Pop"
            }
        }
        return super.getPageTitle(position)
    }

}