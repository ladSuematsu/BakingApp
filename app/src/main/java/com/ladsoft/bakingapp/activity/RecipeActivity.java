package com.ladsoft.bakingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.ladsoft.bakingapp.R;
import com.ladsoft.bakingapp.adapter.RecipeStepsAdapter;
import com.ladsoft.bakingapp.application.BakingAppApplication;
import com.ladsoft.bakingapp.entity.Recipe;
import com.ladsoft.bakingapp.entity.Step;
import com.ladsoft.bakingapp.fragment.RecipeFragment;
import com.ladsoft.bakingapp.fragment.RecipeStepFragment;
import com.ladsoft.bakingapp.manager.SessionManager;
import com.ladsoft.bakingapp.mvp.RecipeMvp;
import com.ladsoft.bakingapp.mvp.model.RecipeModel;
import com.ladsoft.bakingapp.mvp.presenter.RecipePresenter;
import com.ladsoft.bakingapp.mvp.presenter.state.RecipeState;
import com.ladsoft.bakingapp.service.IngredientUpdateService;
import com.ladsoft.bakingapp.util.UiUtils;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;


public class RecipeActivity extends AppCompatActivity implements RecipeMvp.View {
    public static final String EXTRA_RECIPE_ID = "extra_recipe_id";

    @Inject SessionManager sessionManager;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.content) FrameLayout content;
    @BindView (R.id.detail) @Nullable FrameLayout detail;
    @BindString(R.string.recipe_error_generic) String genericErrorMessage;

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
        setupFragments();

        presenter = new RecipePresenter(new RecipeModel());

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
        super.onStop();
    }

    @Override
    public void showRefresh(boolean show) {

    }

    @Override
    public void onRecipeLoaded(@NotNull Recipe recipe) {
        recipeFragment.setDatasource(recipe);
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

    private void setupFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if ((recipeFragment = (RecipeFragment) fragmentManager.findFragmentById(content.getId())) == null) {
            recipeFragment = RecipeFragment.newInstance();
            fragmentManager.beginTransaction()
                    .replace(content.getId(), recipeFragment)
                    .commitNow();
        }

        recipeFragment.setListener(stepAdapterListener);
    }

    private RecipeStepsAdapter.Listener stepAdapterListener = new RecipeStepsAdapter.Listener() {
        @Override
        public void onItemClickListener(Step step) {
            if (detail != null) {
                RecipeStepFragment stepFragment = RecipeStepFragment.newInstance();

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(detail.getId(), stepFragment)
                        .commitNow();

                stepFragment.setDatasource(step);
            } else {
                Intent intent = new Intent(RecipeActivity.this, RecipeStepActivity.class);
                intent.putExtra(RecipeStepActivity.EXTRA_STEP, step);
                startActivity(intent);
            }
        }
    };

    public void setupComponent() {
        BakingAppApplication.appComponent.inject(this);
    }
}
