package com.ladsoft.bakingapp.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.ladsoft.bakingapp.entity.Step
import com.ladsoft.bakingapp.fragment.RecipeStepFragment


class StepSlideshowAdapter(fragmentManager: FragmentManager,
                           val pageListener : RecipeStepFragment.Callback?): FragmentStatePagerAdapter(fragmentManager) {
    private var steps : List<Step> = mutableListOf()

    override fun getItem(position: Int): Fragment {
        return RecipeStepFragment.newInstance().apply {
            setDatasource(steps[position])
            setListener(pageListener)
        }
    }

    override fun getCount(): Int = steps.size

    fun setDataSource(steps: List<Step>?) {
        when {
            steps == null -> this.steps = mutableListOf()
            else -> this.steps = steps
        }

        notifyDataSetChanged()
    }
}