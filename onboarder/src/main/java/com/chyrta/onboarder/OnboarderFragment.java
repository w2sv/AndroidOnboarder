package com.chyrta.onboarder;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class OnboarderFragment extends Fragment {

    private static final String ONBOARDER_PAGE_TITLE = "onboarder_page_title";
    private static final String ONBOARDER_PAGE_TITLE_RES_ID = "onboarder_page_title_res_id";
    private static final String ONBOARDER_PAGE_TITLE_COLOR = "onboarder_page_title_color";
    private static final String ONBOARDER_PAGE_TITLE_TEXT_SIZE = "onboarder_page_title_text_size";
    private static final String ONBOARDER_PAGE_DESCRIPTION = "onboarder_page_description";
    private static final String ONBOARDER_PAGE_DESCRIPTION_RES_ID = "onboarder_page_description_res_id";
    private static final String ONBOARDER_PAGE_DESCRIPTION_COLOR = "onboarder_page_description_color";
    private static final String ONBOARDER_PAGE_DESCRIPTION_TEXT_SIZE = "onboarder_page_description_text_size";
    private static final String ONBOARDER_PAGE_DESCRIPTION_CENTERED = "onboarder_page_description_centered";
    private static final String ONBOARDER_PAGE_IMAGE_RES_ID = "onboarder_page_image_res_id";

    public static OnboarderFragment newInstance(OnboarderPage page) {
        Bundle args = new Bundle();
        args.putString(ONBOARDER_PAGE_TITLE, page.title);
        args.putString(ONBOARDER_PAGE_DESCRIPTION, page.getDescription());
        args.putInt(ONBOARDER_PAGE_TITLE_RES_ID, page.getTitleResourceId());
        args.putInt(ONBOARDER_PAGE_DESCRIPTION_RES_ID, page.getDescriptionResourceId());
        args.putInt(ONBOARDER_PAGE_TITLE_COLOR, page.getTitleColor());
        args.putInt(ONBOARDER_PAGE_DESCRIPTION_COLOR, page.getDescriptionColor());
        args.putInt(ONBOARDER_PAGE_IMAGE_RES_ID, page.getImageResourceId());
        args.putFloat(ONBOARDER_PAGE_TITLE_TEXT_SIZE, page.getTitleTextSize());
        args.putFloat(ONBOARDER_PAGE_DESCRIPTION_TEXT_SIZE, page.getDescriptionTextSize());
        args.putBoolean(ONBOARDER_PAGE_DESCRIPTION_CENTERED, page.isMultilineDescriptionCentered());
        OnboarderFragment fragment = new OnboarderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private boolean onboarderDescriptionTextCentered;
    private TextView tvOnboarderDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        assert bundle != null;

        String onboarderTitle = bundle.getString(ONBOARDER_PAGE_TITLE, null);
        int onboarderTitleResId = bundle.getInt(ONBOARDER_PAGE_TITLE_RES_ID, 0);
        int onboarderTitleColor = bundle.getInt(ONBOARDER_PAGE_TITLE_COLOR, 0);
        float onboarderTitleTextSize = bundle.getFloat(ONBOARDER_PAGE_TITLE_TEXT_SIZE, 0f);
        String onboarderDescription = bundle.getString(ONBOARDER_PAGE_DESCRIPTION, null);
        int onboarderDescriptionResId = bundle.getInt(ONBOARDER_PAGE_DESCRIPTION_RES_ID, 0);
        int onboarderDescriptionColor = bundle.getInt(ONBOARDER_PAGE_DESCRIPTION_COLOR, 0);
        float onboarderDescriptionTextSize = bundle.getFloat(ONBOARDER_PAGE_DESCRIPTION_TEXT_SIZE, 0f);
        onboarderDescriptionTextCentered = bundle.getBoolean(ONBOARDER_PAGE_DESCRIPTION_CENTERED, false);
        int onboarderImageResId = bundle.getInt(ONBOARDER_PAGE_IMAGE_RES_ID, 0);

        View onboarderView = inflater.inflate(R.layout.fragment_onboarder, container, false);
        ImageView ivOnboarderImage = (ImageView) onboarderView.findViewById(R.id.iv_onboarder_image);
        TextView tvOnboarderTitle = (TextView) onboarderView.findViewById(R.id.tv_onboarder_title);
        tvOnboarderDescription = (TextView) onboarderView.findViewById(R.id.tv_onboarder_description);

        if (onboarderTitle != null) {
            tvOnboarderTitle.setText(onboarderTitle);
        }

        if (onboarderTitleResId != 0) {
            tvOnboarderTitle.setText(getResources().getString(onboarderTitleResId));
        }

        if (onboarderDescription != null) {
            tvOnboarderDescription.setText(onboarderDescription);
        }

        if (onboarderDescriptionResId != 0) {
            tvOnboarderDescription.setText(getResources().getString(onboarderDescriptionResId));
        }

        if (onboarderTitleColor != 0) {
            tvOnboarderTitle.setTextColor(ContextCompat.getColor(requireActivity(), onboarderTitleColor));
        }

        if (onboarderDescriptionColor != 0) {
            tvOnboarderDescription.setTextColor(ContextCompat.getColor(requireActivity(), onboarderDescriptionColor));
        }

        if (onboarderImageResId != 0) {
            ivOnboarderImage.setImageDrawable(AppCompatResources.getDrawable(requireActivity(), onboarderImageResId));
        }

        if (onboarderTitleTextSize != 0f) {
            tvOnboarderTitle.setTextSize(onboarderTitleTextSize);
        }

        if (onboarderDescriptionTextSize != 0f) {
            tvOnboarderDescription.setTextSize(onboarderDescriptionTextSize);
        }

        return onboarderView;
    }

    @Override
    public void onResume() {
        super.onResume();

        tvOnboarderDescription.post(() -> {
            if (onboarderDescriptionTextCentered) {
                tvOnboarderDescription.setGravity(Gravity.CENTER);
            } else {
                tvOnboarderDescription.setGravity(tvOnboarderDescription.getLineCount() > 1 ? Gravity.START : Gravity.CENTER);
            }
        });
    }
}