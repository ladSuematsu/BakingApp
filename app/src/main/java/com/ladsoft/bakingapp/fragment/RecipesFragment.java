package com.ladsoft.bakingapp.fragment;

import android.content.Context;
import android.os.Bundle;
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
import com.ladsoft.bakingapp.entity.Recipe;
import com.ladsoft.bakingapp.mvp.RecipesMvp;
import com.ladsoft.bakingapp.mvp.presenter.RecipesPresenter;
import com.ladsoft.bakingapp.util.UiUtils;
import com.ladsoft.bakingapp.view.layoutmanager.decoration.SimplePaddingDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class RecipesFragment extends Fragment implements RecipesMvp.View, SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = RecipesFragment.class.getSimpleName();

    @BindView(R.id.recipes) RecyclerView recipes;
    @BindView(R.id.refresh) SwipeRefreshLayout refresh;

    @Inject RecipesPresenter presenter;

    private RecipesAdapter adapter;
    private RecipesAdapter.Listener recipeAdapterListener;
    private String genericErrorMessage;

    public static RecipesFragment newInstance() {
        return new RecipesFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        genericErrorMessage = getString(R.string.recipes_error);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();
        recipes.setLayoutManager(new LinearLayoutManager(context));
        recipes.addItemDecoration(new SimplePaddingDecoration(getResources().getDimensionPixelSize(R.dimen.list_item_content_margin)));
        adapter = new RecipesAdapter(LayoutInflater.from(context));
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

    public void onRecipesLoaded(List<Recipe> recipes) {
        adapter.setDatasource(recipes);
    }

    @Override
    public void onRecipeLoadError() {
        UiUtils.showSnackbar(getView(), genericErrorMessage, null, Snackbar.LENGTH_LONG, null);
    }

    @Override
    public void showRefresh(boolean show) {
        refresh.setRefreshing(show);
    }
}
