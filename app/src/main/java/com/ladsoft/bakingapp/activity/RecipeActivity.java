package com.ladsoft.bakingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.ladsoft.bakingapp.R;
import com.ladsoft.bakingapp.adapter.RecipeStepsAdapter;
import com.ladsoft.bakingapp.application.BakingAppApplication;
import com.ladsoft.bakingapp.di.component.DaggerRecipeComponent;
import com.ladsoft.bakingapp.di.module.RecipeModule;
import com.ladsoft.bakingapp.entity.Recipe;
import com.ladsoft.bakingapp.entity.Step;
import com.ladsoft.bakingapp.fragment.RecipeFragment;
import com.ladsoft.bakingapp.manager.SessionManager;
import com.ladsoft.bakingapp.service.IngredientUpdateService;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeActivity extends AppCompatActivity {
    public static final String EXTRA_RECIPE = "extra_recipe";

    @Inject SessionManager sessionManager;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.content) FrameLayout content;

    private RecipeFragment recipeFragment;
    private boolean activityCreation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        activityCreation = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        setupComponent(this);

        setupViews();
        setupFragments();

        DaggerRecipeComponent.builder()
                .appComponent(BakingAppApplication.Companion.get(this).getAppComponent())
                .recipeModule(new RecipeModule())
                .build()
                .inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (activityCreation) {
            Intent intent = getIntent();
            Recipe recipe = intent.getParcelableExtra(EXTRA_RECIPE);

            sessionManager.setLastSelectedReceiptId(recipe.getId());
            startService(new Intent(this, IngredientUpdateService.class));

            recipeFragment.setDatasource(recipe);
            activityCreation = false;
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
        FragmentManager fragmentManager = getSupportFragmentManager();

        if ((recipeFragment = (RecipeFragment) fragmentManager.findFragmentById(content.getId())) == null) {
            recipeFragment = RecipeFragment.newInstance();
            fragmentManager.beginTransaction()
                    .replace(content.getId(), recipeFragment)
                    .commit();
        }

        recipeFragment.setListener(stepAdapterListener);
    }

    private RecipeStepsAdapter.Listener stepAdapterListener = new RecipeStepsAdapter.Listener() {
        @Override
        public void onItemClickListener(Step step) {
//            Toast.makeText(RecipeActivity.this, step.getShortDescription(), Toast.LENGTH_LONG).show();

            Intent intent = new Intent(RecipeActivity.this, RecipeStepActivity.class);
            intent.putExtra(RecipeStepActivity.EXTRA_STEP, step);
            startActivity(intent);
        }
    };

    public void setupComponent(RecipeActivity activity) {
//        DaggerAppComponent.builder()
//                .
    }
}
