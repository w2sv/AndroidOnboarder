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
import androidx.fragment.app.activityViewModels
import com.w2sv.androidutils.extensions.show
import com.w2sv.onboarding.databinding.FragmentOnboardingBinding
import com.w2sv.viewboundcontroller.ViewBoundFragment

class OnboardingFragment :
    ViewBoundFragment<FragmentOnboardingBinding>(FragmentOnboardingBinding::class.java) {

    companion object {
        private const val EXTRA_PAGE_POSITION = "com.w2sv.onboarder.extra.PAGE_POSITION"

        fun newInstance(pagePosition: Int): OnboardingFragment =
            OnboardingFragment().apply {
                arguments = bundleOf(
                    EXTRA_PAGE_POSITION to pagePosition
                )
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel by activityViewModels<OnboardingActivity.ViewModel>()

        binding.populate(viewModel.pages[requireArguments().getInt(EXTRA_PAGE_POSITION)])
    }

    private fun FragmentOnboardingBinding.populate(page: OnboardingPage) {
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