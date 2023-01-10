package com.chyrta.onboarder

import android.animation.ArgbEvaluator
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
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
    private lateinit var ibNext: ImageButton
    private lateinit var btnSkip: Button
    private lateinit var btnFinish: Button
    private lateinit var buttonsLayout: FrameLayout
    private lateinit var fab: FloatingActionButton
    private lateinit var divider: View

    private lateinit var colors: List<Int>
    private var shouldUseFloatingActionButton = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_onboarder)

        circleIndicatorView = findViewById(R.id.circle_indicator_view)
        ibNext = findViewById(R.id.ib_next)
        btnSkip = findViewById(R.id.btn_skip)
        btnFinish = findViewById(R.id.btn_finish)
        buttonsLayout = findViewById(R.id.buttons_layout)
        fab = findViewById(R.id.fab)
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
                            if (position < viewPager.adapter!!.itemCount - 1 && position < colors.size - 1) {
                                viewPager.setBackgroundColor(
                                    (evaluator.evaluate(
                                        positionOffset,
                                        colors[position],
                                        colors[position + 1]
                                    ) as Int)
                                )
                            } else {
                                viewPager.setBackgroundColor(colors[colors.size - 1])
                            }
                        }

                        override fun onPageSelected(position: Int) {
                            val lastPagePosition = viewPager.adapter!!.itemCount - 1
                            circleIndicatorView.setCurrentPage(position)
                            ibNext.visibility =
                                if (position == lastPagePosition && !shouldUseFloatingActionButton) View.GONE else View.VISIBLE
                            btnFinish.visibility =
                                if (position == lastPagePosition && !shouldUseFloatingActionButton) View.VISIBLE else View.GONE
                            if (shouldUseFloatingActionButton) fab.setImageResource(if (position == lastPagePosition) R.drawable.ic_done_white_24dp else R.drawable.ic_arrow_forward_white_24dp)
                        }
                    }
                )
            }

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        ibNext.setOnClickListener {
            viewPager.scrollToNextPage()
        }
        btnSkip.setOnClickListener {
            onSkipButtonPressed()
        }
        btnFinish.setOnClickListener {
            onFinishButtonPressed()
        }
        fab.setOnClickListener {
            if (!viewPager.onLastPage)
                viewPager.scrollToNextPage()
            else
                onFinishButtonPressed()
        }
    }

    protected open fun onSkipButtonPressed() {
        viewPager.currentItem = viewPager.adapter!!.itemCount
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

    fun setSkipButtonTitle(title: CharSequence?) {
        btnSkip.text = title
    }

    fun setSkipButtonHidden() {
        btnSkip.visibility = View.GONE
    }

    fun setSkipButtonTitle(@StringRes titleResId: Int) {
        btnSkip.setText(titleResId)
    }

    fun setFinishButtonTitle(title: CharSequence?) {
        btnFinish.text = title
    }

    fun setFinishButtonTitle(@StringRes titleResId: Int) {
        btnFinish.setText(titleResId)
    }

    fun seUseFloatingActionButton(shouldUseFloatingActionButton: Boolean) {
        this.shouldUseFloatingActionButton = shouldUseFloatingActionButton

        if (shouldUseFloatingActionButton) {
            fab.visibility = View.VISIBLE
            setDividerVisibility(View.GONE)
            btnFinish.visibility = View.GONE
            btnSkip.visibility = View.GONE
            ibNext.visibility = View.GONE
            ibNext.isFocusable = false
            buttonsLayout.layoutParams.height = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 96f,
                resources.displayMetrics
            )
                .toInt()
        }
    }
}