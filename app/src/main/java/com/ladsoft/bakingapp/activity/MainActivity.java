package com.ladsoft.bakingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.ladsoft.bakingapp.R;
import com.ladsoft.bakingapp.adapter.RecipesAdapter;
import com.ladsoft.bakingapp.entity.Recipe;
import com.ladsoft.bakingapp.fragment.RecipesFragment;
import com.ladsoft.bakingapp.mvp.RecipeMvp;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    @BindView(R.id.content) FrameLayout content;

    @Inject DispatchingAndroidInjector<Fragment> dispatchingAndroidFragmentInjector;
    private RecipesFragment recipesFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
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
            recipesFragment = RecipesFragment.Companion.newInstance();
            fragmentManager.beginTransaction()
                    .replace(content.getId(), recipesFragment, RecipesFragment.Companion.getTAG())
                    .commitNow();
        }

        recipesFragment.setRecipeAdapterListener(listener);
    }

    private RecipesAdapter.Listener listener = new RecipesAdapter.Listener() {
        @Override
        public void onItemClickListener(Recipe recipe) {
            Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
            intent.putExtra(RecipeMvp.StateContainer.EXTRA_RECIPE_ID, recipe.getId());
            startActivity(intent);
        }
    };

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidFragmentInjector;
    }
}
