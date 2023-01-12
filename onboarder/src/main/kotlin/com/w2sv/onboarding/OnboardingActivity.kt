package com.w2sv.onboarding

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.w2sv.onboarding.databinding.ActivityOnboarderBinding
import com.w2sv.onboarding.extensions.onLastPage
import com.w2sv.onboarding.extensions.scrollToNextPage
import com.w2sv.viewboundcontroller.ViewBoundActivity
import kotlin.properties.Delegates

abstract class OnboardingActivity : ViewBoundActivity<ActivityOnboarderBinding>(ActivityOnboarderBinding::class.java) {

    protected var pages: List<OnboarderPage> by Delegates.observable(listOf()) { _, _, newValue ->
        binding.circleIndicatorView.setPageIndicators(newValue.size)
    }

    private val colors: List<Int> by lazy {
        pages.backgroundColors(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewPager.apply {
            adapter = object : FragmentStateAdapter(this@OnboardingActivity) {
                override fun createFragment(position: Int): Fragment =
                    OnboardingFragment.newInstance(pages[position])

                override fun getItemCount(): Int =
                    pages.size
            }
            registerOnPageChangeCallback(
                object : ViewPager2.OnPageChangeCallback() {

                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                        setBackgroundColor(
                            if (!onLastPage(position))
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
                        binding.circleIndicatorView.setCurrentPage(position)

                        binding.fab.setImageResource(
                            if (onLastPage(position))
                                R.drawable.ic_done_24
                            else
                                R.drawable.ic_arrow_forward_24
                        )
                    }
                }
            )
        }

        binding.setOnClickListeners()
    }

    private fun ActivityOnboarderBinding.setOnClickListeners() {
        fab.setOnClickListener {
            if (!viewPager.onLastPage)
                viewPager.scrollToNextPage()
            else
                onFinishButtonPressed()
        }
    }

    protected abstract fun onFinishButtonPressed()

    @Suppress("unused")
    fun setInactiveIndicatorColor(color: Int) {
        binding.circleIndicatorView.setInactiveIndicatorColor(color)
    }

    @Suppress("unused")
    fun setActiveIndicatorColor(color: Int) {
        binding.circleIndicatorView.setActiveIndicatorColor(color)
    }

    @Suppress("unused")
    fun setFabColor(@ColorInt color: Int) {
        binding.fab.backgroundTintList = ColorStateList.valueOf(color)
    }
}