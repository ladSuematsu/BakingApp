package com.ladsoft.bakingapp.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.util.Util;
import com.ladsoft.bakingapp.R;
import com.ladsoft.bakingapp.adapter.RecipesAdapter;
import com.ladsoft.bakingapp.application.BakingAppApplication;
import com.ladsoft.bakingapp.entity.Recipe;
import com.ladsoft.bakingapp.mvp.model.RecipesModel;
import com.ladsoft.bakingapp.mvp.presenter.RecipesPresenter;
import com.ladsoft.bakingapp.mvp.view.RecipeView;
import com.ladsoft.bakingapp.view.layoutmanager.decoration.SimplePaddingDecoration;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesFragment extends Fragment implements RecipeView {
    @BindView(R.id.recipes) RecyclerView recipes;
    private RecipesAdapter adapter;
    private RecipesAdapter.Listener recipeAdapterListener;
    private RecipesPresenter presenter;

    public static RecipesFragment newInstance() {
        return new RecipesFragment();
    }

    public RecipesFragment() {
        BakingAppApplication.appComponent.inject(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        RecipesModel model = new RecipesModel();
        presenter = new RecipesPresenter(model);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recipes.setLayoutManager(layoutManager);
        recipes.addItemDecoration(new SimplePaddingDecoration(getResources().getDimensionPixelSize(R.dimen.list_item_content_margin)));
        adapter = new RecipesAdapter(LayoutInflater.from(getContext()));
        adapter.setListener(recipeAdapterListener);
        recipes.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadData();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            presenter.detachView();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            presenter.detachView();
        }
    }

    public void setRecipeAdapterListener(RecipesAdapter.Listener recipeAdapterListener) {
        this.recipeAdapterListener = recipeAdapterListener;
    }

    @Override
    public void onRecipesLoaded(@NotNull List<? extends Recipe> recipes) {
        adapter.setDatasource((List<Recipe>) recipes);
    }

    @Override
    public void onRecipeLoadError() {

    }
}
