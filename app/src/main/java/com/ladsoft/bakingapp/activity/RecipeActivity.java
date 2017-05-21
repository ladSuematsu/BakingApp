package com.ladsoft.bakingapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.ladsoft.bakingapp.R;
import com.ladsoft.bakingapp.adapter.RecipeIngredientsAdapter;
import com.ladsoft.bakingapp.fragment.RecipeFragment;
import com.ladsoft.bakingapp.fragment.RecipesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.content) FrameLayout content;

    private RecipeFragment recipeFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        setupViews();

        if(savedInstanceState == null) {
            setupFragments();
        }
    }

    private void setupViews() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setupFragments() {
        recipeFragment = RecipeFragment.newInstance();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(content.getId(), recipeFragment)
                .commit();
    }
}
