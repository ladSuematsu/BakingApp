package com.ladsoft.bakingapp.mvp;

import com.ladsoft.bakingapp.entity.Step;

import java.util.List;

public interface StepsMvp {
    interface View {
        void onStepsLoaded(List<Step> steps);
        void showStep(int position);
    }
}