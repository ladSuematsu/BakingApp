package com.ladsoft.bakingapp.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ladsoft.bakingapp.R;
import com.ladsoft.bakingapp.adapter.RecipesAdapter;
import com.ladsoft.bakingapp.entity.Recipe;
import com.ladsoft.bakingapp.view.layoutmanager.decoration.SimplePaddingDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesFragment extends Fragment {

    @BindView(R.id.recipes) RecyclerView recipes;
    private RecipesAdapter adapter;

    public static RecipesFragment newInstance() {
        return new RecipesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        recipes.setAdapter(adapter);
        setDataSource();
    }

    public void setDataSource() {
        List<Recipe> dataSource = new ArrayList<>();

        for(int i = 1; i <= 25; i++) {
            dataSource.add(new Recipe(i, "Recipe " + i, i, ""));
        }

        adapter.setDatasource(dataSource);
    }
}
