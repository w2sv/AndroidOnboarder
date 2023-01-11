package com.chyrta.onboarder

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.chyrta.onboarder.extensions.onLastPage
import com.chyrta.onboarder.extensions.scrollToNextPage
import com.chyrta.onboarder.views.CircleIndicatorView
import com.google.android.material.floatingactionbutton.FloatingActionButton

abstract class OnboarderActivity : AppCompatActivity() {
    private lateinit var circleIndicatorView: CircleIndicatorView
    private lateinit var viewPager: ViewPager2
    private lateinit var fab: FloatingActionButton

    private lateinit var colors: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_onboarder)

        circleIndicatorView = findViewById(R.id.circle_indicator_view)
        fab = findViewById(R.id.fab)
        viewPager = findViewById<ViewPager2>(R.id.vp_onboarder_pager)
            .apply {
                registerOnPageChangeCallback(
                    object : ViewPager2.OnPageChangeCallback() {

                        override fun onPageScrolled(
                            position: Int,
                            positionOffset: Float,
                            positionOffsetPixels: Int
                        ) {
                            viewPager.setBackgroundColor(
                                if (!viewPager.onLastPage(position))
                                    ColorUtils.blendARGB(
                                        colors[position],
                                        colors[position + 1],
                                        positionOffset
                                    )
                                else
                                    colors.last()
                            )
                        }

                        override fun onPageSelected(position: Int) {
                            circleIndicatorView.setCurrentPage(position)

                            fab.setImageResource(
                                if (onLastPage(position))
                                    R.drawable.ic_done_24
                                else
                                    R.drawable.ic_arrow_forward_24
                            )
                        }
                    }
                )
            }

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        fab.setOnClickListener {
            if (!viewPager.onLastPage)
                viewPager.scrollToNextPage()
            else
                onFinishButtonPressed()
        }
    }

    abstract fun onFinishButtonPressed()

    @Suppress("unused")
    fun setPages(pages: List<OnboarderPage>) {
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment =
                OnboarderFragment.newInstance(pages[position])

            override fun getItemCount(): Int =
                pages.size
        }
        colors = pages.backgroundColors(this)
        circleIndicatorView.setPageIndicators(pages.size)
    }

    @Suppress("unused")
    fun setInactiveIndicatorColor(color: Int) {
        circleIndicatorView.setInactiveIndicatorColor(color)
    }

    @Suppress("unused")
    fun setActiveIndicatorColor(color: Int) {
        circleIndicatorView.setActiveIndicatorColor(color)
    }

    @Suppress("unused")
    fun setFabColor(@ColorInt color: Int) {
        fab.backgroundTintList = ColorStateList.valueOf(color)
    }
}