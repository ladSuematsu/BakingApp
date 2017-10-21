package com.ladsoft.bakingapp.mvp

import com.ladsoft.bakingapp.entity.Step


interface StepsMvp {
    interface View {
        fun showStep(step: Step)
    }
}