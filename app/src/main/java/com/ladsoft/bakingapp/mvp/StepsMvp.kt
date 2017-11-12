package com.ladsoft.bakingapp.mvp

import com.ladsoft.bakingapp.entity.Step


interface StepsMvp {
    interface View {
        fun onStepsLoaded(steps: List<Step>)
        fun showStep(position: Int)
    }
}