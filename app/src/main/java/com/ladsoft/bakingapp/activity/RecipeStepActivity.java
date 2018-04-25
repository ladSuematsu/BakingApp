package com.ladsoft.bakingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.ladsoft.bakingapp.R;
import com.ladsoft.bakingapp.adapter.StepSlideshowAdapter;
import com.ladsoft.bakingapp.application.BakingAppApplication;
import com.ladsoft.bakingapp.entity.Step;
import com.ladsoft.bakingapp.fragment.RecipeStepFragment;
import com.ladsoft.bakingapp.mvp.StepsMvp;
import com.ladsoft.bakingapp.mvp.presenter.StepPresenter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeStepActivity extends AppCompatActivity implements StepsMvp.View, RecipeStepFragment.Callback {
    private static final String LOG_TAG = RecipeStepActivity.class.getSimpleName();

    public static final String EXTRA_STEPS = "extra_steps";
    public static final String EXTRA_STEP_INDEX = "extra_step_index";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.step_pager) ViewPager content;
    private StepSlideshowAdapter slideshowAdapter;
    private boolean activityCreation;
    @Inject StepPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate: " + (savedInstanceState != null ? savedInstanceState.toString() : "NULL"));

        activityCreation = true;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);

        BakingAppApplication.appComponent.inject(this);

        setupViews();
        setupFragments();
    }

    @Override
    protected void onStart() {
        Log.d(LOG_TAG, "onStart");

        presenter.attachView(this);
        if (activityCreation) {

            Intent intent = getIntent();

            presenter.setData(intent.getIntExtra(EXTRA_STEP_INDEX, 0), (List) intent.getParcelableArrayListExtra(EXTRA_STEPS), true);
            presenter.showCurrentStep();

            activityCreation = false;
        }

        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(LOG_TAG, "onStop");

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
        slideshowAdapter = new StepSlideshowAdapter(getSupportFragmentManager(), this);
        content.setAdapter(slideshowAdapter);

        content.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                presenter.setCurrentStepIndex(position);
            }
        });
    }

    @Override
    public void onStepsLoaded(@NotNull List<? extends Step> steps) {
        slideshowAdapter.setDataSource(steps);
    }

    @Override
    public void onNextPress() {
        presenter.nextStep();
    }

    @Override
    public void onPreviousPress() {
        presenter.previousStep();
    }

    @Override
    public Step onVisible() {
        return presenter.stepData();
    }

    @Override
    public void showStep(int position) {
        Log.d(LOG_TAG, "Navigating to index " + position);
        content.setCurrentItem(position, true);
    }
}
