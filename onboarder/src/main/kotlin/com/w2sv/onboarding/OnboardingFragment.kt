package com.w2sv.onboarding

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.annotation.StyleableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.w2sv.onboarding.databinding.FragmentOnboarderBinding
import com.w2sv.viewboundcontroller.ViewBoundFragment

class OnboardingFragment :
    ViewBoundFragment<FragmentOnboarderBinding>(FragmentOnboarderBinding::class.java) {

    companion object {
        fun newInstance(page: OnboardingPage): OnboardingFragment =
            OnboardingFragment().apply {
                arguments = bundleOf(
                    OnboardingPage.EXTRA to page
                )
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        @Suppress("DEPRECATION")
        requireArguments()
            .getParcelable<OnboardingPage>(OnboardingPage.EXTRA)!!
            .populateView(view)
    }

    private fun OnboardingPage.populateView(view: View) {
        if (emblemDrawableRes != null)
            with(binding.emblemIv) {
                visibility = View.VISIBLE
                setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireContext(),
                        emblemDrawableRes
                    )!!
                )
            }
        else if (emblemText != null) {
            with(binding.emblemTv) {
                visibility = View.VISIBLE
                text = emblemText
                emblemTextSize?.let {
                    textSize = it
                }
            }
        }

        binding.titleTv.populate(
            titleText,
            titleTextRes,
            titleColorRes,
            titleSize,
            titleFontRes
        )
        binding.descriptionTv.populate(
            descriptionText,
            descriptionTextRes,
            descriptionColorRes,
            descriptionSize,
            descriptionFontRes,
            binding.scrollView
        )

        actionLayoutRes?.let {
            layoutInflater.inflate(it, binding.actionLayout, true)
            binding.actionLayout.visibility = View.VISIBLE
        }

        onViewCreatedListener?.invoke(view)
    }

    private fun TextView.populate(
        text: CharSequence?,
        @StringRes textRes: Int?,
        @ColorRes colorRes: Int,
        size: Float?,
        @StyleableRes fontRes: Int?,
        visibilityAlterationTargetView: View = this
    ) {
        if (text == null && textRes == null)
            visibilityAlterationTargetView.visibility = View.GONE
        else {
            this.text = if (textRes != null)
                resources.getText(textRes)
            else
                text!!

            setTextColor(ContextCompat.getColor(requireContext(), colorRes))

            size?.let {
                textSize = it
            }
            fontRes?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    this.typeface = resources.getFont(it)
                }
            }
        }
    }
}