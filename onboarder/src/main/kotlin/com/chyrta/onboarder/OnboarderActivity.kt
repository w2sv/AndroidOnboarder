package com.chyrta.onboarder

import android.animation.ArgbEvaluator
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.chyrta.onboarder.extensions.onLastPage
import com.chyrta.onboarder.extensions.scrollToNextPage
import com.chyrta.onboarder.views.CircleIndicatorView
import com.google.android.material.floatingactionbutton.FloatingActionButton

@Suppress("unused")
abstract class OnboarderActivity : AppCompatActivity() {
    private lateinit var circleIndicatorView: CircleIndicatorView
    private lateinit var viewPager: ViewPager2
    private lateinit var buttonsLayout: FrameLayout
    private lateinit var fab: FloatingActionButton
    private lateinit var divider: View

    private lateinit var colors: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_onboarder)

        circleIndicatorView = findViewById(R.id.circle_indicator_view)
        buttonsLayout = findViewById(R.id.buttons_layout)
        fab = findViewById<FloatingActionButton?>(R.id.fab)
            .apply {
                setDividerVisibility(View.GONE)
                buttonsLayout.layoutParams.height = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 96f,
                    resources.displayMetrics
                )
                    .toInt()
            }
        divider = findViewById(R.id.divider)
        viewPager = findViewById<ViewPager2>(R.id.vp_onboarder_pager)
            .apply {
                registerOnPageChangeCallback(
                    object : ViewPager2.OnPageChangeCallback() {

                        private val evaluator: ArgbEvaluator by lazy {
                            ArgbEvaluator()
                        }

                        override fun onPageScrolled(
                            position: Int,
                            positionOffset: Float,
                            positionOffsetPixels: Int
                        ) {
                            viewPager.setBackgroundColor(
                                if (position < viewPager.adapter!!.itemCount - 1 && position < colors.lastIndex)
                                    evaluator.evaluate(
                                        positionOffset,
                                        colors[position],
                                        colors[position + 1]
                                    ) as Int
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

    fun setPages(pages: List<OnboarderPage>) {
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment =
                OnboarderFragment.newInstance(pages[position])

            override fun getItemCount(): Int =
                pages.size
        }
        colors = pages.backgroundColors()
        circleIndicatorView.setPageIndicators(pages.size)
    }

    fun setInactiveIndicatorColor(color: Int) {
        circleIndicatorView.setInactiveIndicatorColor(color)
    }

    fun setActiveIndicatorColor(color: Int) {
        circleIndicatorView.setActiveIndicatorColor(color)
    }

    fun setDividerColor(@ColorInt color: Int) {
        divider.setBackgroundColor(color)
    }

    fun setDividerHeight(heightInDp: Int) {
        divider.layoutParams.height = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, heightInDp.toFloat(),
            resources.displayMetrics
        ).toInt()
    }

    fun setDividerVisibility(dividerVisibility: Int) {
        divider.visibility = dividerVisibility
    }

    fun setFabColor(@ColorInt color: Int) {
        fab.setBackgroundColor(color)
    }
}