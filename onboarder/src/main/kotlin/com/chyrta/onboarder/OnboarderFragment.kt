package com.chyrta.onboarder

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.annotation.StyleableRes
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
        layout.findViewById<ImageView>(R.id.emblem_iv)
            .apply {
                setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireContext(),
                        this@populateView.emblemDrawable
                    )
                )
            }
        layout.findViewById<TextView>(R.id.tv_onboarder_title)
            .apply {
                populate(titleTextId, titleColor, titleSize, titleFont)
            }
        descriptionTV = layout.findViewById<TextView>(R.id.tv_onboarder_description)
            .apply {
                populate(descriptionTextId, descriptionColor, descriptionSize, descriptionFont)
            }
    }

    private fun TextView.populate(
        @StringRes text: Int?,
        @ColorRes color: Int,
        size: Float?,
        @StyleableRes font: Int?
    ) {
        if (text == null)
            visibility = View.GONE
        else {
            this.text = resources.getString(text)
            setTextColor(ContextCompat.getColor(requireContext(), color))

            size?.let {
                textSize = it
            }
            font?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    this.typeface = resources.getFont(it)
                }
            }
        }
    }
}