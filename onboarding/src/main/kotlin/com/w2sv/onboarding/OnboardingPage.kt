package com.w2sv.onboarding

import android.app.Activity
import android.os.Parcelable
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
class OnboardingPage(
    /**
     * title
     */
    @StringRes val titleTextRes: Int? = null,
    val titleText: CharSequence? = null,
    @ColorRes val titleColorRes: Int? = null,
    val titleSize: Float? = null,
    @FontRes val titleFontRes: Int? = null,

    /**
     * description
     */
    @StringRes val descriptionTextRes: Int? = null,
    val descriptionText: CharSequence? = null,
    @ColorRes val descriptionColorRes: Int? = null,
    val descriptionSize: Float? = null,
    @FontRes val descriptionFontRes: Int? = null,

    /**
     * emblem
     */
    @DrawableRes val emblemDrawableRes: Int? = null,
    val emblemText: CharSequence? = null,

    /**
     * misc
     */
    @LayoutRes val actionLayoutRes: Int? = null,
    @ColorRes val backgroundColorRes: Int? = null,

    /**
     * listeners
     */
    val onViewCreatedListener: ((View, Activity) -> Unit)? = null,
    val onPageSelectedListener: ((View?, Activity) -> Unit)? = null
) : Parcelable {

    companion object {
        const val EXTRA = "com.w2sv.onboarding.extra.OnboarderPage"
    }
}