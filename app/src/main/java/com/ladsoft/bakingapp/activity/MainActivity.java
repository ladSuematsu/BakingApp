package com.ladsoft.bakingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.ladsoft.bakingapp.R;
import com.ladsoft.bakingapp.adapter.RecipesAdapter;
import com.ladsoft.bakingapp.entity.Recipe;
import com.ladsoft.bakingapp.fragment.RecipeFragment;
import com.ladsoft.bakingapp.fragment.RecipesFragment;
import com.ladsoft.bakingapp.mvp.RecipeMvp;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.content) FrameLayout content;
    private RecipesFragment recipesFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupFragments(savedInstanceState);
    }

    private void setupFragments(Bundle savedInstanceState) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            recipesFragment = (RecipesFragment) fragmentManager.findFragmentById(content.getId());
        } else {
            recipesFragment = RecipesFragment.newInstance();
            fragmentManager.beginTransaction()
                    .replace(content.getId(), recipesFragment, RecipesFragment.TAG)
                    .commitNow();
        }

        recipesFragment.setRecipeAdapterListener(listener);
    }

    private RecipesAdapter.Listener listener = new RecipesAdapter.Listener() {
        @Override
        public void onItemClickListener(Recipe recipe) {
            Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
            intent.putExtra(RecipeMvp.StateContainer.Companion.getEXTRA_RECIPE_ID(), recipe.getId());
            startActivity(intent);
        }
    };
}
