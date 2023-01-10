package com.chyrta.onboarder.extensions

import androidx.viewpager2.widget.ViewPager2

val ViewPager2.onLastPage: Boolean
    get() = onLastPage(currentItem)

fun ViewPager2.onLastPage(position: Int): Boolean =
    position == adapter!!.itemCount - 1

fun ViewPager2.scrollToNextPage() {
    currentItem += 1
}