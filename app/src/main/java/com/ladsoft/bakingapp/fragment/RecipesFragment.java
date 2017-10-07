package com.ladsoft.bakingapp.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ladsoft.bakingapp.R;
import com.ladsoft.bakingapp.adapter.RecipesAdapter;
import com.ladsoft.bakingapp.application.BakingAppApplication;
import com.ladsoft.bakingapp.entity.Recipe;
import com.ladsoft.bakingapp.mvp.model.RecipesModel;
import com.ladsoft.bakingapp.mvp.presenter.RecipesPresenter;
import com.ladsoft.bakingapp.mvp.view.RecipeView;
import com.ladsoft.bakingapp.util.UiUtils;
import com.ladsoft.bakingapp.view.layoutmanager.decoration.SimplePaddingDecoration;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesFragment extends Fragment implements RecipeView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recipes) RecyclerView recipes;
    @BindView(R.id.refresh) SwipeRefreshLayout refresh;
    private RecipesAdapter adapter;
    private RecipesAdapter.Listener recipeAdapterListener;
    private RecipesPresenter presenter;
    private String genericErrorMessage;

    public static RecipesFragment newInstance() {
        return new RecipesFragment();
    }

    public RecipesFragment() {
        BakingAppApplication.appComponent.inject(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        genericErrorMessage = getString(R.string.recipes_error);

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
        refresh.setOnRefreshListener(this);
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
    }

    @Override
    public void onStop() {
        presenter.detachView();
        super.onStop();
    }

    @Override
    public void onRefresh() {
        presenter.loadData();
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
        UiUtils.INSTANCE.showSnackbar(getView(), genericErrorMessage, null, Snackbar.LENGTH_LONG, null);
    }

    @Override
    public void showRefresh(boolean show) {
        refresh.setRefreshing(show);
    }
}
