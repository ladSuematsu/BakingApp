package com.ladsoft.bakingapp.mvp.model;

import android.os.Handler;
import android.util.Log;

import com.ladsoft.bakingapp.data.database.repository.DatabaseIngredientRepository;
import com.ladsoft.bakingapp.data.database.repository.DatabaseRecipeRepository;
import com.ladsoft.bakingapp.data.database.repository.DatabaseStepRepository;
import com.ladsoft.bakingapp.data.repository.RecipeRepository;
import com.ladsoft.bakingapp.entity.Recipe;
import com.ladsoft.bakingapp.mvp.RecipesMvp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RecipesModel implements RecipesMvp.Model {
    private static final String LOG_TAG = RecipesModel.class.getSimpleName();

    private final Handler handler = new Handler();
    private final TaskHandler taskHandler = new TaskHandler();
    private final RecipeRepository recipesRepository;
    private final DatabaseRecipeRepository cacheRecipesRepository;
    private final DatabaseIngredientRepository cacheIngredientRepository;
    private final DatabaseStepRepository cacheStepRepository;

    RecipesMvp.Model.Callback recipesModelListener;

    public RecipesModel(RecipeRepository recipesRepository,
                        DatabaseRecipeRepository cacheRecipesRepository,
                        DatabaseIngredientRepository cacheIngredientRepository,
                        DatabaseStepRepository cacheStepRepository) {
        this.recipesRepository = recipesRepository;
        this.cacheRecipesRepository = cacheRecipesRepository;
        this.cacheIngredientRepository = cacheIngredientRepository;
        this.cacheStepRepository = cacheStepRepository;

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
        this.recipesModelListener = listener;
    }

    @Override
    public void detachRecipesModelListener() {
        this.recipesModelListener = null;
    }

    @Override
    public void loadRecipes() {
        taskHandler.postTask(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Recipe> recipes = recipesRepository.recipes();

                    HashSet<Long> resultIds = new HashSet<>();
                    for (Recipe recipe : recipes) {
                        resultIds.add(recipe.getId());
                    }

                    if (recipes.isEmpty()) {
                        cacheRecipesRepository.deletePreserving(new ArrayList<>(resultIds));
                    }

                    // Save into local database
                    for (Recipe recipe : recipes) {
                        try {
                            cacheRecipesRepository.insert(recipe);
                            cacheIngredientRepository.insert(recipe.getIngredients());
                            cacheStepRepository.insert(recipe.getSteps());
                        } catch (Exception e) {
                            Log.e(LOG_TAG, "loadRecipes(): Something went while processing the recipes", e);
                        }
                    }
                } catch (Exception e) {
                    Log.e(LOG_TAG, "loadRecipes(): Something went wrong", e);
                } finally {
                    try {
                        // Always load from local database
                        final List<Recipe> recipes = cacheRecipesRepository.loadRecipes();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (recipesModelListener != null) {
                                    recipesModelListener.onDataLoaded(recipes);
                                }
                            }
                        });

                    } catch (Exception e){
                        if (recipesModelListener != null) {
                            recipesModelListener.onDataLoadError();
                        }
                    }
                }
            }
        });
    }
}
