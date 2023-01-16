package com.w2sv.onboarding.extensions

import androidx.viewpager2.widget.ViewPager2

val ViewPager2.onLastPage: Boolean
    get() = onLastPage(currentItem)

fun ViewPager2.onLastPage(position: Int): Boolean =
    position == adapter!!.itemCount - 1

fun fragmentStateAdapterChildFragmentTag(position: Int): String =
    "f$position"