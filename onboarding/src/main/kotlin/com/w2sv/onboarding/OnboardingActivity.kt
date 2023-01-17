package com.w2sv.onboarding

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.w2sv.onboarding.databinding.ActivityOnboardingBinding
import com.w2sv.onboarding.extensions.fragmentStateAdapterChildFragmentTag
import com.w2sv.onboarding.extensions.onLastPage
import com.w2sv.viewboundcontroller.ViewBoundActivity

abstract class OnboardingActivity :
    ViewBoundActivity<ActivityOnboardingBinding>(ActivityOnboardingBinding::class.java) {

    protected abstract fun onOnboardingFinished()

    protected abstract fun getPages(): List<OnboardingPage>

    private lateinit var backgroundColors: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setPages()
        binding.setOnClickListeners()
    }

    private fun ActivityOnboardingBinding.setOnClickListeners() {
        fab.setOnClickListener {
            with(viewPager) {
                if (!onLastPage) {
                    setCurrentItem(currentItem + 1, true)
                    return@setOnClickListener
                }
            }
            onOnboardingFinished()
        }
    }

    private fun setPages() {
        val pages = getPages()
        backgroundColors = pages.map {
            ContextCompat.getColor(
                this,
                it.backgroundColorRes ?: R.color.onboarding_background
            )
        }
        binding.viewPager.setBy(pages)
    }

    private fun ViewPager2.setBy(pages: List<OnboardingPage>) {
        adapter = object : FragmentStateAdapter(this@OnboardingActivity) {
            override fun createFragment(position: Int): Fragment =
                OnboardingFragment.newInstance(pages[position])

            override fun getItemCount(): Int =
                pages.size
        }
        binding.pageIndicator.attachTo(this)

        registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                /**
                 * Interpolate background color
                 */
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

                /**
                 * Change [binding].fab drawable;
                 * Invoke [OnboardingPage.onPageFullyVisibleListener] if != null
                 */
                override fun onPageSelected(position: Int) {
                    binding.fab.setImageResource(
                        if (onLastPage(position))
                            R.drawable.ic_done_24
                        else
                            R.drawable.ic_arrow_forward_24
                    )
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)

                    if (state == ViewPager2.SCROLL_STATE_IDLE && currentItem != lastShownPage) {
                        pages[currentItem].onPageFullyVisibleListener?.invoke(
                            supportFragmentManager.findFragmentByTag(
                                fragmentStateAdapterChildFragmentTag(currentItem)
                            )!!
                                .view,
                            this@OnboardingActivity
                        )

                        lastShownPage = currentItem
                    }
                }

                private var lastShownPage: Int? = null
            }
        )
    }

    @Suppress("unused")
    fun setFabColor(@ColorInt color: Int) {
        binding.fab.backgroundTintList = ColorStateList.valueOf(color)
    }
}