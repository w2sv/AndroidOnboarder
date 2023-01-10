package com.chyrta.onboarder.extensions

import androidx.viewpager2.widget.ViewPager2

val ViewPager2.onLastPage: Boolean
    get() = currentItem == adapter!!.itemCount - 1

fun ViewPager2.scrollToNextPage() {
    currentItem += 1
}