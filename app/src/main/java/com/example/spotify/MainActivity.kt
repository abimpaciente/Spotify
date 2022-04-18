package com.example.spotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.example.spotify.databinding.ActivityMainBinding
import com.example.spotify.view.MediaAdapter
import com.example.spotify.view.SliderMediaAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val tabIcons = intArrayOf(
        androidx.constraintlayout.widget.R.drawable.abc_btn_radio_to_on_mtrl_015,
        androidx.constraintlayout.widget.R.drawable.abc_btn_radio_to_on_mtrl_000,
        androidx.constraintlayout.widget.R.drawable.abc_ic_commit_search_api_mtrl_alpha
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager = findViewById<ViewPager>(R.id.vpMedia)
        viewPager.adapter = SliderMediaAdapter(supportFragmentManager)

        val tabLayout = findViewById<TabLayout>(R.id.tlMedia)
        tabLayout.setupWithViewPager(viewPager)


        tabLayout.getTabAt(0)?.setIcon(tabIcons[0])
        tabLayout.getTabAt(1)?.setIcon(tabIcons[1])
        tabLayout.getTabAt(2)?.setIcon(tabIcons[2])

    }
}