package com.example.touristpark.view.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.touristpark.view.DescriptionFragment;
import com.example.touristpark.view.ReviewFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private int numOfTabs;
    private Bundle bundle;
    public PagerAdapter(@NonNull FragmentManager fm, int behavior, Bundle bundle) {
        super(fm, behavior);
        numOfTabs = behavior;
        this.bundle = bundle;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
         switch (position){
             case 0:
                 DescriptionFragment descriptionFragment = new DescriptionFragment();
                 descriptionFragment.setArguments(bundle);
                 return descriptionFragment;
             case 1:
                 ReviewFragment reviewFragment = new ReviewFragment();
                 reviewFragment.setArguments(bundle);
                 return reviewFragment;
         }
         return null;
    }

    @Override
    public int getCount() {
       return numOfTabs;
    }
}
