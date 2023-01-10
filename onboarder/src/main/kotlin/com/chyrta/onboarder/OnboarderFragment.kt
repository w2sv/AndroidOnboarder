package com.chyrta.onboarder

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
        page
            .populatedView(
                inflater.inflate(
                    R.layout.fragment_onboarder,
                    container,
                    false
                )
            )

    private fun OnboarderPage.populatedView(layout: View): View {
        layout.findViewById<ImageView>(R.id.iv_onboarder_image)
            .apply {
                setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireContext(),
                        this@populatedView.drawable
                    )
                )
            }
        layout.findViewById<TextView>(R.id.tv_onboarder_title)
            .apply {
                text = resources.getString(title.text)
                textSize = title.size
                setTextColor(ContextCompat.getColor(requireContext(), title.color))
            }
        descriptionTV = layout.findViewById<TextView?>(R.id.tv_onboarder_description)
            .apply {
                text = resources.getString(description.text)
                textSize = description.size
                setTextColor(ContextCompat.getColor(requireContext(), description.color))
            }

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(descriptionTV) {
            gravity = if (page.centerDescription)
                Gravity.CENTER
            else
                if (descriptionTV.lineCount > 1) Gravity.START else Gravity.CENTER
        }
    }
}