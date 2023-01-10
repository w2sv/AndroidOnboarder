package com.chyrta.onboarder;

import android.graphics.drawable.Drawable;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public class OnboarderPage {

    public String title;
    public String description;
    public Drawable imageResource;
    @StringRes
    public int titleResourceId;
    @StringRes public int descriptionResourceId;
    @DrawableRes
    public int imageResourceId;
    @ColorRes
    public int titleColor;
    @ColorRes public int descriptionColor;
    @ColorRes public int backgroundColor;
    public float titleTextSize;
    public float descriptionTextSize;
    public boolean multilineDescriptionCentered;

    public OnboarderPage(String title, String description) {
        this.title = title;
        this.description = description;
        this.backgroundColor = R.color.black_transparent;
    }

    public OnboarderPage(String title, String description, int imageResourceId) {
        this.title = title;
        this.description = description;
        this.imageResourceId = imageResourceId;
        this.backgroundColor = R.color.black_transparent;
    }

    public OnboarderPage(String title, String description, Drawable imageResource) {
        this.title = title;
        this.description = description;
        this.imageResource = imageResource;
        this.backgroundColor = R.color.black_transparent;
    }

    public OnboarderPage(int title, int description) {
        this.titleResourceId = title;
        this.descriptionResourceId = description;
        this.backgroundColor = R.color.black_transparent;
    }

    public OnboarderPage(int title, int description, int imageResourceId) {
        this.titleResourceId = title;
        this.descriptionResourceId = description;
        this.imageResourceId = imageResourceId;
        this.backgroundColor = R.color.black_transparent;
    }

    public OnboarderPage(int title, int description, Drawable imageResource) {
        this.titleResourceId = title;
        this.descriptionResourceId = description;
        this.imageResource = imageResource;
        this.backgroundColor = R.color.black_transparent;
    }
}
