package com.ladsoft.bakingapp.mvp.model;

import android.os.Handler;
import android.util.Log;

import com.ladsoft.bakingapp.data.database.repository.DatabaseIngredientRepository;
import com.ladsoft.bakingapp.data.database.repository.DatabaseRecipeRepository;
import com.ladsoft.bakingapp.data.database.repository.DatabaseStepRepository;
import com.ladsoft.bakingapp.entity.Recipe;
import com.ladsoft.bakingapp.mvp.RecipeMvp;

public class RecipeModel implements RecipeMvp.Model {
    private static final String LOG_TAG = RecipeModel.class.getSimpleName();

    private final DatabaseRecipeRepository cacheRecipesRepository;
    private final DatabaseIngredientRepository cacheIngredientsRepository;
    private final DatabaseStepRepository cacheStepsRepository;

    private final Handler mainHandler = new Handler();
    private final TaskHandler taskHandler = new TaskHandler();
    RecipeMvp.Model.Callback modelListener;

    public RecipeModel(DatabaseRecipeRepository cacheRecipesRepository,
                       DatabaseIngredientRepository cacheIngredientsRepository,
                       DatabaseStepRepository cacheStepsRepository) {
        this.cacheRecipesRepository = cacheRecipesRepository;
        this.cacheIngredientsRepository = cacheIngredientsRepository;
        this.cacheStepsRepository = cacheStepsRepository;

        this.taskHandler.start();
        this.taskHandler.prepareWorkerHandler();
    }

    @Override
    protected void finalize() throws Throwable {
        taskHandler.quitSafely();
        super.finalize();
    }

    @Override
    public void setListener(Callback listener) {
        modelListener = listener;
    }

    @Override
    public void detachListener() {
        modelListener = null;
    }

    @Override
    public void loadRecipe(final long recipeId) {
        taskHandler.postTask(new Runnable() {
            @Override
            public void run() {
                try {
                    final Recipe recipe = cacheRecipesRepository.loadRecipe(recipeId);
                    if (recipe != null) {
                        recipe.setIngredients(cacheIngredientsRepository.loadForRecipeId(recipeId));
                        recipe.setSteps(cacheStepsRepository.loadForRecipeId(recipeId));
                    }

                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (modelListener != null) {
                                if (recipe == null) {
                                    modelListener.onDataLoadError();
                                } else {
                                    modelListener.onDataLoaded(recipe);
                                }
                            }
                        }
                    });
                } catch (Exception e){
                    Log.e(LOG_TAG, "Something went wrong", e);

                    if (modelListener != null) {
                        modelListener.onDataLoadError();
                    }
                }
            }
        });
    }
}
