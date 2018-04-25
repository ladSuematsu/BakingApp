package com.ladsoft.bakingapp.mvp.presenter

import com.ladsoft.bakingapp.entity.Step
import com.ladsoft.bakingapp.mvp.StepsMvp


open class StepPresenter : Presenter<StepsMvp.View>() {

    private val steps : MutableList<Step> = mutableListOf()
    private var stepIndex = 0
    private var autoLoad = true

    open fun setData(initialIndex: Int, steps: List<Step>?, autoLoad: Boolean = true) {
        if (steps != null) {
            when {
                initialIndex < steps.size -> stepIndex = initialIndex
                else -> stepIndex = 0
            }

            this.steps.addAll(steps)

            when {
                autoLoad -> view?.onStepsLoaded(this.steps)
                else -> this.autoLoad = false
            }
        }
    }

    open fun showCurrentStep() {
        steps.apply {
            if (size > 0) {
                if (!autoLoad) {
                    view?.onStepsLoaded(steps)
                    autoLoad = true
                }

                view?.showStep(stepIndex)
            }
        }
    }

    open fun setCurrentStepIndex(index: Int) {
        stepIndex = index
    }

    open fun nextStep() {
        if (stepIndex < this.steps.size) {
            stepIndex = if (stepIndex + 1 == this.steps.size) stepIndex else stepIndex + 1

            view?.showStep(stepIndex)
        }
    }

    open fun previousStep() {
        if (stepIndex > 0) {
            stepIndex = if (stepIndex == 0) stepIndex else stepIndex - 1

            view?.showStep(stepIndex)
        }
    }

    open fun stepData(): Step {
        return steps[stepIndex]
    }

}