package com.w2sv.onboarding

import android.app.Activity
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.annotation.StyleableRes
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
class OnboardingPage(
    /**
     * title
     */
    @StringRes val titleTextRes: Int? = null,
    val titleText: CharSequence? = null,
    @ColorRes val titleColorRes: Int = R.color.onboarding_title,
    val titleSize: Float? = null,
    @StyleableRes val titleFontRes: Int? = null,

    /**
     * description
     */
    @StringRes val descriptionTextRes: Int? = null,
    val descriptionText: CharSequence? = null,
    @ColorRes val descriptionColorRes: Int = R.color.onboarding_description,
    val descriptionSize: Float? = null,
    @StyleableRes val descriptionFontRes: Int? = null,

    /**
     * emblem
     */
    @DrawableRes val emblemDrawableRes: Int? = null,
    val emblemText: CharSequence? = null,
    val emblemTextSize: Float? = null,

    /**
     * other
     */
    @LayoutRes val actionLayoutRes: Int? = null,
    @ColorRes val backgroundColorRes: Int = R.color.onboarding_background,
    val onViewCreatedListener: ((View, Activity) -> Unit)? = null,
    val onPageSelectedListener: ((View?, Activity) -> Unit)? = null
) : Parcelable {

    companion object {
        const val EXTRA = "com.w2sv.onboarding.extra.OnboarderPage"
    }
}

fun Iterable<OnboardingPage>.backgroundColors(context: Context): List<Int> =
    map { ContextCompat.getColor(context, it.backgroundColorRes) }