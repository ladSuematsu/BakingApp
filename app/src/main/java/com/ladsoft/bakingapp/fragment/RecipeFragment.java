package com.ladsoft.bakingapp.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ladsoft.bakingapp.R;
import com.ladsoft.bakingapp.adapter.RecipeIngredientsAdapter;
import com.ladsoft.bakingapp.adapter.RecipeStepsAdapter;
import com.ladsoft.bakingapp.entity.Ingredient;
import com.ladsoft.bakingapp.entity.Recipe;
import com.ladsoft.bakingapp.entity.Step;
import com.ladsoft.bakingapp.view.layoutmanager.decoration.SimplePaddingDecoration;

import java.util.ArrayList;
import java.util.List;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ingredients.setNestedScrollingEnabled(false);
        ingredients.setLayoutManager(new LinearLayoutManager(getContext()));
        ingredients.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        ingredientsAdapter = new RecipeIngredientsAdapter(LayoutInflater.from(getContext()));
        ingredients.setAdapter(ingredientsAdapter);

        steps.setNestedScrollingEnabled(false);
        steps.setLayoutManager(new LinearLayoutManager(getContext()));
        steps.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        stepsAdapter = new RecipeStepsAdapter(LayoutInflater.from(getContext()));
        stepsAdapter.setListener(listener);
        steps.setAdapter(stepsAdapter);
    }

    public void setListener(RecipeStepsAdapter.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(Recipe recipe) {
        if (recipe == null) { return; }

        ingredientsAdapter.setDatasource(recipe.getIngredients());
        stepsAdapter.setDatasource(recipe.getSteps());
    }
}
