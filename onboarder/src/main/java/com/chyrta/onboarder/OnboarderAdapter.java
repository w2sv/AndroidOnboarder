package com.chyrta.onboarder;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class OnboarderAdapter extends FragmentStateAdapter {

    List<OnboarderPage> pages;

    public OnboarderAdapter(List<OnboarderPage> pages, @NonNull FragmentManager fragmentManager,
                            @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        this.pages = pages;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return OnboarderFragment.newInstance(pages.get(position));
    }

    @Override
    public int getItemCount() {
        return pages.size();
    }
}
