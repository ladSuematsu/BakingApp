package com.ladsoft.bakingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
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
import com.ladsoft.bakingapp.entity.Recipe;
import com.ladsoft.bakingapp.entity.Step;
import com.ladsoft.bakingapp.fragment.RecipeFragment;
import com.ladsoft.bakingapp.fragment.RecipeStepFragment;
import com.ladsoft.bakingapp.manager.SessionManager;
import com.ladsoft.bakingapp.mvp.RecipeMvp;
import com.ladsoft.bakingapp.mvp.StepsMvp;
import com.ladsoft.bakingapp.mvp.presenter.RecipePresenter;
import com.ladsoft.bakingapp.mvp.presenter.StepPresenter;
import com.ladsoft.bakingapp.mvp.presenter.state.RecipeState;
import com.ladsoft.bakingapp.service.IngredientUpdateService;
import com.ladsoft.bakingapp.util.UiUtils;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class RecipeActivity extends AppCompatActivity
        implements HasSupportFragmentInjector, RecipeMvp.View, RecipeStepFragment.Callback, StepsMvp.View {
    public static final String EXTRA_RECIPE_ID = "extra_recipe_id";

    @Inject DispatchingAndroidInjector<Fragment> dispatchingAndroidFragmentInjector;

    @Inject RecipePresenter presenter;
    @Inject StepPresenter stepPresenter;
    @Inject SessionManager sessionManager;
    @Inject RecipeState state;

    @BindView(R.id.toolbar) Toolbar toolbar;

    @BindView(R.id.recipe_name) TextView recipeName;

    @BindView(R.id.servings) TextView servings;

    @BindView(R.id.ingredient_count) TextView ingredientCount;

    @BindView(R.id.step_count) TextView stepCount;

    @BindView(R.id.content) FrameLayout content;

    @Nullable @BindView(R.id.detail) ViewPager detail;

    @BindString(R.string.recipe_error_generic) String genericErrorMessage;

    private StepSlideshowAdapter slideshowAdapter;

    private RecipeFragment recipeFragment;

    private static final String DATA_STATE = "data_state";

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidFragmentInjector;
    }

    private RecipeStepsAdapter.Listener stepAdapterListener = new RecipeStepsAdapter.Listener() {
        @Override
        public void onItemClickListener(int itemIndex, @Nullable List<Step> steps) {
            if (detail != null) {
                detail.setVisibility(View.VISIBLE);

                stepPresenter.setCurrentStepIndex(itemIndex);
                stepPresenter.showCurrentStep();
            } else {
                Intent intent = new Intent(RecipeActivity.this, RecipeStepActivity.class);
                intent.putParcelableArrayListExtra(RecipeStepActivity.EXTRA_STEPS, (ArrayList<? extends Parcelable>) steps);
                intent.putExtra(RecipeStepActivity.EXTRA_STEP_INDEX, itemIndex);
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        setupViews();
        setupFragments(savedInstanceState != null);

        if (savedInstanceState == null) {
            state.load(getIntent().getExtras());
        } else {
            state.load(savedInstanceState.getSerializable(DATA_STATE));
        }

        startService(new Intent(this, IngredientUpdateService.class));
        presenter.setData(state);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attachView(this);
        stepPresenter.attachView(this);
        presenter.loadData();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(DATA_STATE, presenter.getData().getStateMap());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        presenter.detachView();
        stepPresenter.detachView();
        super.onStop();
    }

    private void setupViews() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent parentIntent = NavUtils.getParentActivityIntent(RecipeActivity.this);
                if (parentIntent == null) {
                    throw new IllegalStateException("No Parent Activity Intent");
                } else if (NavUtils.shouldUpRecreateTask(RecipeActivity.this, parentIntent)) {
                    TaskStackBuilder.create(RecipeActivity.this)
                            .addNextIntentWithParentStack(parentIntent)
                            .startActivities();
                } else {
                    NavUtils.navigateUpTo(RecipeActivity.this, parentIntent);
                }
            }
        });

//        setSupportActionBar(toolbar);
    }

    public void setupFragments(boolean isRestore) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        recipeFragment = (RecipeFragment) fragmentManager.findFragmentById(content.getId());
        if (recipeFragment  == null) {
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
            slideshowAdapter = new StepSlideshowAdapter(fragmentManager, this);
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

    @Override
    public void onNextPress() {
        stepPresenter.nextStep();

    }

    @Override
    public void onPreviousPress() {
        stepPresenter.previousStep();

    }

    @Nullable
    @Override
    public Step onVisible() {
        return stepPresenter.stepData();
    }

    @Override
    public void showRefresh(boolean show) {}

    @Override
    public void onRecipeLoaded(Recipe recipe) {
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
        UiUtils.showSnackbar(content, genericErrorMessage, null, Snackbar.LENGTH_LONG, null);
    }

    @Override
    public void onStepsLoaded(List<Step> steps) {
        if (detail != null) {
            slideshowAdapter.setDataSource(steps);
        }
    }

    @Override
    public void showStep(int position) {
        Log.d("RECIPE_STEP", "Navigating to index $position");
        detail.setCurrentItem(position, true);
    }
}
