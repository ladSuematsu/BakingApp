package com.ladsoft.bakingapp.mvp.presenter;

import com.ladsoft.bakingapp.entity.Step;
import com.ladsoft.bakingapp.mvp.StepsMvp;

import java.util.ArrayList;
import java.util.List;


public class StepPresenter extends Presenter<StepsMvp.View> {
    private List<Step> steps  = new ArrayList<>();
    private int stepIndex = 0;
    private boolean autoLoad = true;

    public void setData(int initialIndex, List<Step> steps, boolean autoLoad) {
        if (!isViewAttached()) { return; }

        if (steps != null) {
             stepIndex = initialIndex < steps.size()
                            ? initialIndex
                            : 0;

            this.steps.addAll(steps);

            if (autoLoad) {
                getView().onStepsLoaded(this.steps);
            } else {
                this.autoLoad = false;
            }
        }
    }

    public void showCurrentStep() {
        if (!isViewAttached()) { return; }

        if (steps.size() > 0) {
            if (!autoLoad) {
                getView().onStepsLoaded(steps);
                autoLoad = true;
            }

            getView().showStep(stepIndex);
        }
    }

    public void setCurrentStepIndex(int index) {
        stepIndex = index;
    }

    public void nextStep() {
        if (!isViewAttached()) { return; }

        if (stepIndex < this.steps.size()) {
            stepIndex = stepIndex + 1 == this.steps.size()
                            ? stepIndex
                            : stepIndex + 1;

            getView().showStep(stepIndex);
        }
    }

    public void previousStep() {
        if (!isViewAttached()) { return; }

        if (stepIndex > 0) {
            stepIndex = stepIndex == 0 ? stepIndex : stepIndex - 1;

            getView().showStep(stepIndex);
        }
    }

    public Step stepData() {
        return steps.get(stepIndex);
    }
}