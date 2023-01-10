package com.chyrta.onboarder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

class OnboarderFragment : Fragment() {

    companion object {
        fun newInstance(page: OnboarderPage): OnboarderFragment =
            OnboarderFragment().apply {
                arguments = bundleOf(
                    OnboarderPage.EXTRA to page
                )
            }
    }

    private val page: OnboarderPage by lazy {
        @Suppress("DEPRECATION")
        requireArguments().getParcelable(OnboarderPage.EXTRA)!!
    }

    private lateinit var descriptionTV: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(
            R.layout.fragment_onboarder,
            container,
            false
        )
            .apply {
                page
                    .populateView(this)
            }

    private fun OnboarderPage.populateView(layout: View) {
        layout.findViewById<ImageView>(R.id.iv_onboarder_image)
            .apply {
                setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireContext(),
                        this@populateView.drawable
                    )
                )
            }
        layout.findViewById<TextView>(R.id.tv_onboarder_title)
            .apply {
                populate(titleText, titleColor, titleSize)
            }
        descriptionTV = layout.findViewById<TextView>(R.id.tv_onboarder_description)
            .apply {
                populate(descriptionText, descriptionColor, descriptionSize)
            }
    }

    private fun TextView.populate(@StringRes text: Int, @ColorRes color: Int, size: Float) {
        this.text = resources.getString(text)
        setTextColor(ContextCompat.getColor(requireContext(), color))
        size.let {
            if (it != 0f)
                textSize = it
        }
    }
}