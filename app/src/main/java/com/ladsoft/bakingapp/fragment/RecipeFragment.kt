package com.ladsoft.bakingapp.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ladsoft.bakingapp.R
import com.ladsoft.bakingapp.adapter.RecipeIngredientsAdapter
import com.ladsoft.bakingapp.adapter.RecipeStepsAdapter
import com.ladsoft.bakingapp.entity.Recipe

import butterknife.BindView
import butterknife.ButterKnife

class RecipeFragment : Fragment() {

    @JvmField @BindView(R.id.ingredients)
    internal var ingredients: RecyclerView? = null

    @JvmField @BindView(R.id.steps)
    internal var steps: RecyclerView? = null

    private lateinit var ingredientsAdapter: RecipeIngredientsAdapter
    private lateinit var stepsAdapter: RecipeStepsAdapter
    private lateinit var listener: RecipeStepsAdapter.Listener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recipe, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ingredients!!.isNestedScrollingEnabled = false
        ingredients!!.layoutManager = LinearLayoutManager(context)
        ingredientsAdapter = RecipeIngredientsAdapter(LayoutInflater.from(context))
        ingredients!!.adapter = ingredientsAdapter

        steps!!.isNestedScrollingEnabled = false
        steps!!.layoutManager = LinearLayoutManager(context)
        steps!!.addItemDecoration(DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL))
        stepsAdapter = RecipeStepsAdapter(LayoutInflater.from(context))
        stepsAdapter.setListener(listener)
        steps!!.adapter = stepsAdapter
    }

    fun setListener(listener: RecipeStepsAdapter.Listener) {
        this.listener = listener
    }

    fun setDatasource(recipe: Recipe?) {
        ingredientsAdapter.setDatasource(recipe?.ingredients?.toMutableList())
        stepsAdapter.setDatasource(recipe?.steps?.toMutableList())
    }

    companion object {

        fun newInstance(): RecipeFragment {
            return RecipeFragment()
        }
    }
}
