package com.chyrta.onboarder

import android.content.Context
import android.os.Parcelable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.StyleableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.parcelize.Parcelize

@Parcelize
data class OnboarderPage(
    /**
     * title
     */
    @StringRes val titleTextId: Int? = null,
    val titleText: CharSequence? = null,
    @ColorRes val titleColor: Int = R.color.white,
    val titleSize: Float? = null,
    @StyleableRes val titleFont: Int? = null,

    /**
     * description
     */
    @StringRes val descriptionTextId: Int? = null,
    val descriptionText: CharSequence? = null,
    @ColorRes val descriptionColor: Int = R.color.white,
    val descriptionSize: Float? = null,
    @StyleableRes val descriptionFont: Int? = null,

    /**
     * emblem
     */
    @DrawableRes
    val emblemDrawable: Int? = null,
    val emblemText: CharSequence? = null,
    val emblemTextSize: Float? = null,

    /**
     * other
     */
    val getActionLayoutFragment: (() -> Fragment)? = null,
    @ColorRes
    val backgroundColor: Int = R.color.black_transparent
) : Parcelable {

    companion object {
        const val EXTRA = "com.chyrta.onboarder.extra.OnboarderPage"
    }
}

fun Iterable<OnboarderPage>.backgroundColors(context: Context): List<Int> =
    map { ContextCompat.getColor(context, it.backgroundColor) }