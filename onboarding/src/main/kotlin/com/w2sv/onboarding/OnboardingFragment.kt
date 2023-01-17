package com.w2sv.onboarding

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.w2sv.onboarding.databinding.FragmentOnboarderBinding
import com.w2sv.onboarding.extensions.show
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

        binding.populate(
            @Suppress("DEPRECATION")
            requireArguments()
                .getParcelable(OnboardingPage.EXTRA)!!
        )
    }

    private fun FragmentOnboarderBinding.populate(page: OnboardingPage) {
        when {
            page.emblemDrawableRes != null -> {
                emblemIv.show()
                emblemIv.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireContext(),
                        page.emblemDrawableRes
                    )!!
                )
            }

            page.emblemText != null -> {
                emblemTv.show()
                emblemTv.text = page.emblemText
            }

            else -> Unit
        }

        titleTv.populate(
            page.titleText,
            page.titleTextRes,
            page.titleColorRes,
            page.titleSize,
            page.titleFontRes
        )
        descriptionTv.populate(
            page.descriptionText,
            page.descriptionTextRes,
            page.descriptionColorRes,
            page.descriptionSize,
            page.descriptionFontRes
        )

        page.actionLayoutRes?.let {
            actionLayout.show()
            layoutInflater.inflate(it, actionLayout, true)
        }

        page.onViewCreatedListener?.invoke(
            requireView(),
            requireActivity() as AppCompatActivity
        )
    }

    private fun TextView.populate(
        text: CharSequence?,
        @StringRes textRes: Int?,
        @ColorRes colorRes: Int?,
        size: Float?,
        @FontRes fontRes: Int?
    ) {
        when {
            textRes != null -> this.text = resources.getText(textRes)
            text != null -> this.text = text
            else -> return
        }

        colorRes?.let {
            setTextColor(ContextCompat.getColor(requireContext(), it))
        }
        size?.let {
            textSize = it
        }
        if (fontRes != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            typeface = resources.getFont(fontRes)
    }
}