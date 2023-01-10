package com.chyrta.onboarder

import android.os.Parcelable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class OnboarderPage(
    val title: TextViewProperties,
    val description: TextViewProperties,
    @DrawableRes
    val drawable: Int = 0,
    @ColorRes
    val backgroundColor: Int = R.color.black_transparent,
    val centerDescription: Boolean = false
) : Parcelable {

    companion object {
        const val EXTRA = "com.chyrta.onboarder.extra.OnboarderPage"
    }

    @Parcelize
    data class TextViewProperties(
        @StringRes val text: Int,
        @ColorRes val color: Int,
        val size: Float
    ) :
        Parcelable
}

fun Iterable<OnboarderPage>.backgroundColors(): List<Int> =
    map { it.backgroundColor }