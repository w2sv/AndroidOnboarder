package com.w2sv.onboarding

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider

internal class OnboardingViewModel(val pages: List<OnboardingPage>, val backgroundColors: List<Int>) :
    androidx.lifecycle.ViewModel() {

    class Factory(private val pages: List<OnboardingPage>, private val context: Context) :
        ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T =
            OnboardingViewModel(
                pages,
                pages.map {
                    ContextCompat.getColor(
                        context,
                        it.backgroundColorRes ?: R.color.onboarding_background
                    )
                }
            ) as T
    }
}