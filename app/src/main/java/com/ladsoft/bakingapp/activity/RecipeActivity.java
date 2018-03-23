package com.ladsoft.bakingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ladsoft.bakingapp.R;
import com.ladsoft.bakingapp.adapter.RecipeStepsAdapter;
import com.ladsoft.bakingapp.adapter.StepSlideshowAdapter;
import com.ladsoft.bakingapp.application.BakingAppApplication;
import com.ladsoft.bakingapp.entity.Recipe;
import com.ladsoft.bakingapp.entity.Step;
import com.ladsoft.bakingapp.fragment.RecipeFragment;
import com.ladsoft.bakingapp.fragment.RecipeStepFragment;
import com.ladsoft.bakingapp.manager.SessionManager;
import com.ladsoft.bakingapp.mvp.RecipeMvp;
import com.ladsoft.bakingapp.mvp.StepsMvp;
import com.ladsoft.bakingapp.mvp.model.RecipeModel;
import com.ladsoft.bakingapp.mvp.presenter.RecipePresenter;
import com.ladsoft.bakingapp.mvp.presenter.StepPresenter;
import com.ladsoft.bakingapp.mvp.presenter.state.RecipeState;
import com.ladsoft.bakingapp.service.IngredientUpdateService;
import com.ladsoft.bakingapp.util.UiUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeActivity extends AppCompatActivity implements RecipeMvp.View, RecipeStepFragment.Callback, StepsMvp.View {
    public static final String EXTRA_RECIPE_ID = "extra_recipe_id";

    @Inject SessionManager sessionManager;
    @Inject RecipeMvp.Model model;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recipe_name) TextView recipeName;
    @BindView(R.id.servings) TextView servings;
    @BindView(R.id.ingredient_count) TextView ingredientCount;
    @BindView(R.id.step_count) TextView stepCount;
    @BindView(R.id.content) FrameLayout content;
    @BindView (R.id.detail) @Nullable ViewPager detail;
    @BindString(R.string.recipe_error_generic) String genericErrorMessage;

    private StepSlideshowAdapter slideshowAdapter;

    private RecipeFragment recipeFragment;

    private RecipePresenter presenter;
    private String DATA_STATE = "data_state";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        setupComponent();

        setupViews();
        setupFragments(savedInstanceState != null);

        presenter = new RecipePresenter(model);
        stepPresenter = new StepPresenter();

        RecipeState state;
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            state = new RecipeState(intent.getExtras());
        } else {
            state = new RecipeState(savedInstanceState.getSerializable(DATA_STATE));
        }

        startService(new Intent(this, IngredientUpdateService.class));
        presenter.setData(state);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attachView(this);
        stepPresenter.attachView(RecipeActivity.this);
        presenter.loadData();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(DATA_STATE, presenter.getState().getStateMap());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        presenter.detachView();
        stepPresenter.detachView();
        super.onStop();
    }

    @Override
    public void showRefresh(boolean show) {

    }

    @Override
    public void onRecipeLoaded(@NotNull Recipe recipe) {
        recipeName.setText(recipe.getName());
        toolbar.setTitle(recipe.getName());

        servings.setText(String.format(getString(R.string.recipe_serving_count_format),
                recipe.getServings()));

        ingredientCount.setText(String.format(getString(R.string.recipe_ingredient_count_format),
                recipe.getIngredientCount()));

        stepCount.setText(String.format(getString(R.string.recipe_step_count_format),
                recipe.getStepCount()));

        recipeFragment.setDatasource(recipe);
        stepPresenter.setData(0, recipe.getSteps(), false);
    }

    @Override
    public void onRecipeLoadError() {
        UiUtils.INSTANCE.showSnackbar(content, genericErrorMessage, null, Snackbar.LENGTH_LONG, null);
    }

    private void setupViews() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void setupFragments(boolean isRestore) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if ((recipeFragment = (RecipeFragment) fragmentManager.findFragmentById(content.getId())) == null) {
            recipeFragment = RecipeFragment.newInstance();
            fragmentManager.beginTransaction()
                    .replace(content.getId(), recipeFragment)
                    .commitNow();
        }

        if (detail != null && !isRestore) {
            detail.setVisibility(View.INVISIBLE);
        }

        recipeFragment.setListener(stepAdapterListener);

        if (detail != null) {
            slideshowAdapter = new StepSlideshowAdapter(getSupportFragmentManager(), this);
            detail.setAdapter(slideshowAdapter);

            detail.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    stepPresenter.setCurrentStepIndex(position);
                }
            });
        }
    }

    private StepPresenter stepPresenter;
    private RecipeStepsAdapter.Listener stepAdapterListener = new RecipeStepsAdapter.Listener() {
        @Override
        public void onItemClickListener(int itemIndex, List<Step> steps) {
            if (detail != null) {
                detail.setVisibility(View.VISIBLE);

                stepPresenter.setCurrentStepIndex(itemIndex);
                stepPresenter.showCurrentStep();
            } else {
                Intent intent = new Intent(RecipeActivity.this, RecipeStepActivity.class);
                intent.putParcelableArrayListExtra(RecipeStepActivity.EXTRA_STEPS, (ArrayList<Step>) steps);
                intent.putExtra(RecipeStepActivity.EXTRA_STEP_INDEX, itemIndex);
                startActivity(intent);
            }
        }
    };

    public void setupComponent() {
        BakingAppApplication.appComponent.inject(this);
    }

    @Override
    public void onNextPress() {
        stepPresenter.nextStep();
    }

    @Override
    public void onPreviousPress() {
        stepPresenter.previousStep();
    }

    @Override
    public Step onVisible() {
        return stepPresenter.stepData();
    }

    @Override
    public void showStep(int position) {
        Log.d("RECIPE_STEP", "Navigating to index " + position);
        detail.setCurrentItem(position, true);
    }

    @Override
    public void onStepsLoaded(@NotNull List<? extends Step> steps) {
        if (detail != null) {
            slideshowAdapter.setDataSource(steps);
        }
    }

}
