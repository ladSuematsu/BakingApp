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
import com.ladsoft.bakingapp.entity.Recipe;
import com.ladsoft.bakingapp.entity.Step;
import com.ladsoft.bakingapp.fragment.RecipeFragment;
import com.ladsoft.bakingapp.fragment.RecipeStepFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeStepActivity extends AppCompatActivity {
    public static String EXTRA_STEP = "extra_step";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.content) FrameLayout content;
    private RecipeStepFragment recipeStepFragment;
    private boolean activityCreation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        activityCreation = true;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        setupViews();
        setupFragments();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (activityCreation) {
            Intent intent = getIntent();
            recipeStepFragment.setDatasource((Step) intent.getParcelableExtra(EXTRA_STEP));
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

        if ((recipeStepFragment = (RecipeStepFragment) fragmentManager.findFragmentById(content.getId())) == null) {
            recipeStepFragment = RecipeStepFragment.newInstance();
            fragmentManager.beginTransaction()
                    .replace(content.getId(), recipeStepFragment)
                    .commit();
        }
    }
}
