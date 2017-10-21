package com.ladsoft.bakingapp.mvp.presenter

import com.ladsoft.bakingapp.entity.Step
import com.ladsoft.bakingapp.mvp.StepsMvp


class StepPresenter : Presenter<StepsMvp.View>() {

    private val steps : MutableList<Step> = mutableListOf()
    private var stepIndex = 0

    fun setData(initialIndex : Int, steps : List<Step>? ) {
        if (steps != null) {
            stepIndex = if (initialIndex < steps.size)  initialIndex else 0
            this.steps.addAll(steps)
        }
    }

    fun showCurrentStep() {
        steps.apply {
            if (size > 0) {
                view?.showStep(get(stepIndex))
            }
        }
    }

    fun nextStep() {
        if (stepIndex < this.steps.size) {
            this.steps.apply {
                view?.showStep(get(stepIndex++))
            }
        }
    }

    fun previousStep() {
        if (stepIndex > 0) {
            this.steps.apply {
                view?.showStep(get(--stepIndex))
            }
        }
    }

}