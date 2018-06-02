package com.ladsoft.bakingapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ladsoft.bakingapp.R;
import com.ladsoft.bakingapp.adapter.RecipeIngredientsAdapter;
import com.ladsoft.bakingapp.adapter.RecipeStepsAdapter;
import com.ladsoft.bakingapp.entity.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeFragment extends Fragment {
    @BindView(R.id.ingredients) RecyclerView ingredients;
    @BindView(R.id.steps) RecyclerView steps;

    private RecipeIngredientsAdapter ingredientsAdapter;
    private RecipeStepsAdapter stepsAdapter;
    private RecipeStepsAdapter.Listener listener;

    public static RecipeFragment newInstance() {
        return new RecipeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();
        ingredients.setNestedScrollingEnabled(false);
        ingredients.setLayoutManager(new LinearLayoutManager(context));
        ingredientsAdapter = new RecipeIngredientsAdapter(LayoutInflater.from(context));
        ingredients.setAdapter(ingredientsAdapter);

        steps.setNestedScrollingEnabled(false);
        steps.setLayoutManager(new LinearLayoutManager(context));
        steps.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        stepsAdapter = new RecipeStepsAdapter(LayoutInflater.from(context));
        stepsAdapter.setListener(listener);
        steps.setAdapter(stepsAdapter);
    }

    public void setListener(RecipeStepsAdapter.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(Recipe recipe) {
        ingredientsAdapter.setDatasource(recipe.getIngredients());
        stepsAdapter.setDatasource(recipe.getSteps());
    }
}
