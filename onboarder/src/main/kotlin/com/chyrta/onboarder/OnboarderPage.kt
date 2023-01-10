package com.chyrta.onboarder

import android.os.Parcelable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class OnboarderPage(
    @StringRes val titleText: Int,
    @ColorRes val titleColor: Int = R.color.white,
    val titleSize: Float = 0f,
    @StringRes val descriptionText: Int,
    @ColorRes val descriptionColor: Int = R.color.white,
    val descriptionSize: Float = 0f,
    @DrawableRes
    val drawable: Int = 0,
    @ColorRes
    val backgroundColor: Int = R.color.black_transparent
) : Parcelable {

    companion object {
        const val EXTRA = "com.chyrta.onboarder.extra.OnboarderPage"
    }
}

fun Iterable<OnboarderPage>.backgroundColors(): List<Int> =
    map { it.backgroundColor }