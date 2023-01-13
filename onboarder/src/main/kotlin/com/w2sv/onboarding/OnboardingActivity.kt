package com.w2sv.onboarding

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.w2sv.onboarding.databinding.ActivityOnboarderBinding
import com.w2sv.onboarding.extensions.onLastPage
import com.w2sv.onboarding.extensions.scrollToNextPage
import com.w2sv.viewboundcontroller.ViewBoundActivity

abstract class OnboardingActivity :
    ViewBoundActivity<ActivityOnboarderBinding>(ActivityOnboarderBinding::class.java) {

    private lateinit var backgroundColors: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
    protected fun setPages(pages: List<OnboardingPage>) {
        binding.viewPager.apply {
            adapter = object : FragmentStateAdapter(this@OnboardingActivity) {
                override fun createFragment(position: Int): Fragment =
                    OnboardingFragment.newInstance(pages[position])

                override fun getItemCount(): Int =
                    pages.size
            }
            binding.pageIndicator.attachTo(this)

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
                                    backgroundColors[position],
                                    backgroundColors[position + 1],
                                    positionOffset
                                )
                            else
                                backgroundColors.last()
                        )
                    }

                    override fun onPageSelected(position: Int) {
                        binding.fab.setImageResource(
                            if (onLastPage(position))
                                R.drawable.ic_done_24
                            else
                                R.drawable.ic_arrow_forward_24
                        )

                        pages[position].onPageSelectedListener?.invoke(
                            supportFragmentManager.findFragmentByTag("f$position")!!.view,
                            this@OnboardingActivity
                        )
                    }
                }
            )
        }

        backgroundColors = pages.map {
            ContextCompat.getColor(
                this,
                it.backgroundColorRes ?: R.color.onboarding_background
            )
        }
    }

    @Suppress("unused")
    fun setFabColor(@ColorInt color: Int) {
        binding.fab.backgroundTintList = ColorStateList.valueOf(color)
    }
}