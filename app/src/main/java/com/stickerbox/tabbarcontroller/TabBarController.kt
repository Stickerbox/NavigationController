package com.stickerbox.tabbarcontroller

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.stickerbox.tabbarcontroller.fragments.HomeFragment
import com.stickerbox.tabbarcontroller.fragments.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class TabBarController : AppCompatActivity() {

    private val adapter by lazy { TabAdapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabbar_container.adapter = adapter
        tabbar_container.offscreenPageLimit = adapter.count

        tabbar_container.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                invalidateOptionsMenu()
            }
        })

        tabbar.setOnNavigationItemSelectedListener {
            tabbar_container.currentItem = it.order
            true
        }

        tabbar.setOnNavigationItemReselectedListener {
            adapter.getItem(tabbar_container.currentItem).popToRoot()
        }

    }

    override fun onBackPressed() {
        if (!adapter.getItem(tabbar_container.currentItem).onBackPressed()) {
            super.onBackPressed()
        }
    }

    class TabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        private val fragments = mutableMapOf<Int, NavigationController>()

        override fun getItem(position: Int): NavigationController {
            if (fragments[position] == null) {
                fragments[position] = when (position) {
                    0 -> NavigationController.newInstance(HomeFragment())
                    1 -> NavigationController.newInstance(SearchFragment())
                    else -> throw IllegalArgumentException("Unsupported position: $position")
                }
            }
            return fragments[position]!!
        }

        override fun getCount() = 2
    }

}