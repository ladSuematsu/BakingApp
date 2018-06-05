package com.ladsoft.bakingapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ladsoft.bakingapp.entity.Step;
import com.ladsoft.bakingapp.fragment.RecipeStepFragment;

import java.util.ArrayList;
import java.util.List;

public class StepSlideshowAdapter extends FragmentStatePagerAdapter {
    private final RecipeStepFragment.Callback listener;
    private List<Step> steps = new ArrayList<>();

    public StepSlideshowAdapter(FragmentManager fragmentManager, RecipeStepFragment.Callback listener) {
        super(fragmentManager);
        this.listener = listener;
    }

    @Override
    public Fragment getItem(int position)  {
        RecipeStepFragment fragment = RecipeStepFragment.newInstance();
        fragment.setListener(listener);

        return fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return steps.size();
    }

    public void setDataSource(List<Step> steps) {
        this.steps = steps == null
                ? new ArrayList<Step>()
                : steps;

        notifyDataSetChanged();
    }
}