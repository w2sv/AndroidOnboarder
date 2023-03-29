package com.w2sv.onboarding

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity

data class OnboardingPage(
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
    val onViewCreatedListener: ((View, AppCompatActivity) -> Unit)? = null,
    val onPageFullyVisibleListener: ((View?, AppCompatActivity) -> Unit)? = null
)