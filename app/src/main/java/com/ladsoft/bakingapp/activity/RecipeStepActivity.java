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
import com.ladsoft.bakingapp.entity.Step;
import com.ladsoft.bakingapp.fragment.RecipeStepFragment;
import com.ladsoft.bakingapp.mvp.StepsMvp;
import com.ladsoft.bakingapp.mvp.presenter.StepPresenter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeStepActivity extends AppCompatActivity implements StepsMvp.View, RecipeStepFragment.Callback {
    public static final String EXTRA_STEPS = "extra_steps";
    public static final String EXTRA_STEP_INDEX = "extra_step_index";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.content) FrameLayout content;
    private RecipeStepFragment recipeStepFragment;
    private boolean activityCreation;
    private StepPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        activityCreation = true;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);

        presenter = new StepPresenter();

        setupViews();
        setupFragments();
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.attachView(this);
        if (activityCreation) {

            Intent intent = getIntent();

            presenter.setData(intent.getIntExtra(EXTRA_STEP_INDEX, 0), (List) intent.getParcelableArrayListExtra(EXTRA_STEPS));
            presenter.showCurrentStep();

            activityCreation = false;
        }
    }

    @Override
    protected void onStop() {
        presenter.detachView();
        super.onStop();
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

        recipeStepFragment.setListener(this);
    }

    @Override
    public void showStep(@NotNull Step step) {
        recipeStepFragment.setDatasource(step);
    }

    @Override
    public void onNextPress() {
        presenter.nextStep();
    }

    @Override
    public void onPreviousPress() {
        presenter.previousStep();
    }
}
